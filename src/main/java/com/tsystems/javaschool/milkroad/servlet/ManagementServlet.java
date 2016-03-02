package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.AttributeDTO;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.model.PaymentMethodEnum;
import com.tsystems.javaschool.milkroad.model.PaymentStatusEnum;
import com.tsystems.javaschool.milkroad.model.ShippingMethodEnum;
import com.tsystems.javaschool.milkroad.model.ShippingStatusEnum;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sergey on 29.02.2016.
 */
@WebServlet(name = "ManagementServlet")
public class ManagementServlet extends HttpServlet {
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = (String) request.getAttribute("action");
        if (action != null && !action.isEmpty()) {
            final CatalogService catalogService = MilkroadAppContext.getInstance().getCatalogService();
            switch (action) {
                case "viewOrders" : {
                    try {
                        final List<OrderDTO> orders = MilkroadAppContext.getInstance().getOrderService().getAllOrders();
                        request.setAttribute("orders", orders);
                        request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    }
                    return;
                }
                case "addProduct" : {
                    try {
                        final List<CategoryDTO> categories = catalogService.getAllCategories();
                        final List<AttributeDTO> attributes = catalogService.getAllAttributes();
                        request.setAttribute("categories", categories);
                        request.setAttribute("attributes", attributes);
                        request.getRequestDispatcher("/addproduct.jsp").forward(request, response);
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    }
                    return;
                }
            }
        }
        response.sendRedirect("/");
    }

    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            final Set<String> errors;
            final Object errorsObj = request.getAttribute("errors");
            //noinspection unchecked
            errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
            switch (action) {
                case "updateOrder" : {
                    final OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setId(Long.valueOf(request.getParameter("orderID")));
                    orderDTO.setPaymentMethod(PaymentMethodEnum.valueOf(request.getParameter("paymentMethod")));
                    orderDTO.setPaymentStatus(PaymentStatusEnum.valueOf(request.getParameter("paymentStatus")));
                    orderDTO.setShippingMethod(ShippingMethodEnum.valueOf(request.getParameter("shippingMethod")));
                    orderDTO.setShippingStatus(ShippingStatusEnum.valueOf(request.getParameter("shippingStatus")));
                    try {
                        MilkroadAppContext.getInstance().getOrderService().updateOrder(orderDTO);
                        response.sendRedirect("/management?action=viewOrders");
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    }
                    return;
                }

                case "updateCategory" : {
                    if (errors.size() == 0) {
                        final CategoryDTO categoryDTO = new CategoryDTO();
                        categoryDTO.setId(Long.valueOf(request.getParameter("categoryID")));
                        categoryDTO.setName(request.getParameter("categoryName"));
                        categoryDTO.setDescription(request.getParameter("categoryDescription"));
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().updateCategory(categoryDTO);
                            response.sendRedirect("/management?action=addProduct");
                            return;
                        } catch (final MilkroadServiceException e) {
                            if (e.getType() == MilkroadServiceException.Type.CATEGORY_ALREADY_EXISTS) {
                                errors.add("CATEGORY_DUPLICATE_ERROR");
                            } else {
                                request.setAttribute("message", "DB error! Please, try later");
                                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                                return;
                            }
                        }
                    }
                    request.setAttribute("errors", errors);
                    request.setAttribute("action", "addProduct");
                    doGet(request, response);
                    return;
                }

                case "createCategory" : {
                    if (errors.size() == 0) {
                        final CategoryDTO categoryDTO = new CategoryDTO();
                        categoryDTO.setName(request.getParameter("categoryName"));
                        categoryDTO.setDescription(request.getParameter("categoryDescription"));
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().createCategory(categoryDTO);
                            response.sendRedirect("/management?action=addProduct");
                            return;
                        } catch (final MilkroadServiceException e) {
                            if (e.getType() == MilkroadServiceException.Type.CATEGORY_ALREADY_EXISTS) {
                                errors.add("CATEGORY_DUPLICATE_ERROR");
                            } else {
                                request.setAttribute("message", "DB error! Please, try later");
                                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                                return;
                            }
                        }
                    }
                    request.setAttribute("errors", errors);
                    request.setAttribute("action", "addProduct");
                    doGet(request, response);
                    return;
                }

                case "updateAttribute" : {
                    if (errors.size() == 0) {
                        final AttributeDTO attributeDTO = new AttributeDTO();
                        attributeDTO.setId(Long.valueOf(request.getParameter("attributeID")));
                        attributeDTO.setName(request.getParameter("attributeName"));
                        attributeDTO.setDescription(request.getParameter("attributeDescription"));
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().updateAttribute(attributeDTO);
                            response.sendRedirect("/management?action=addProduct");
                            return;
                        } catch (final MilkroadServiceException e) {
                            if (e.getType() == MilkroadServiceException.Type.ATTRIBUTE_ALREADY_EXISTS) {
                                errors.add("ATTRIBUTE_DUPLICATE_ERROR");
                            } else {
                                request.setAttribute("message", "DB error! Please, try later");
                                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                                return;
                            }
                        }
                    }
                    request.setAttribute("errors", errors);
                    request.setAttribute("action", "addProduct");
                    doGet(request, response);
                    return;
                }

                case "createAttribute" : {
                    if (errors.size() == 0) {
                        final AttributeDTO attributeDTO = new AttributeDTO();
                        attributeDTO.setName(request.getParameter("attributeName"));
                        attributeDTO.setDescription(request.getParameter("attributeDescription"));
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().createAttribute(attributeDTO);
                            response.sendRedirect("/management?action=addProduct");
                            return;
                        } catch (final MilkroadServiceException e) {
                            if (e.getType() == MilkroadServiceException.Type.ATTRIBUTE_ALREADY_EXISTS) {
                                errors.add("ATTRIBUTE_DUPLICATE_ERROR");
                            } else {
                                request.setAttribute("message", "DB error! Please, try later");
                                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                                return;
                            }
                        }
                    }
                    request.setAttribute("errors", errors);
                    request.setAttribute("action", "addProduct");
                    doGet(request, response);
                    return;
                }
            }
        }
        response.sendRedirect("/");
    }
}
