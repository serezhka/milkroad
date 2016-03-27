package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.*;
import com.tsystems.javaschool.milkroad.model.PaymentMethodEnum;
import com.tsystems.javaschool.milkroad.model.PaymentStatusEnum;
import com.tsystems.javaschool.milkroad.model.ShippingMethodEnum;
import com.tsystems.javaschool.milkroad.model.ShippingStatusEnum;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.StatisticsService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sergey on 29.02.2016.
 */
@Deprecated
@WebServlet(name = "ManagementServlet")
public class ManagementServlet extends HttpServlet {
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = (String) request.getAttribute("action");
        if (action != null && !action.isEmpty()) {
            final CatalogService catalogService = MilkroadAppContext.getInstance().getCatalogService();
            switch (action) {
                case "editOrders": {
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
                case "editCategories": {
                    try {
                        final List<CategoryDTO> categories = catalogService.getAllCategories();
                        final List<AttributeDTO> attributes = catalogService.getAllAttributes();
                        request.setAttribute("categories", categories);
                        request.setAttribute("attributes", attributes);
                        request.getRequestDispatcher("/categories-attributes.jsp").forward(request, response);
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    }
                    return;
                }
                case "editProducts": {
                    try {
                        final List<ProductDTO> products = catalogService.getAllProducts();
                        final List<CategoryDTO> categories = catalogService.getAllCategories();
                        final List<AttributeDTO> attributes = catalogService.getAllAttributes();
                        request.setAttribute("categories", categories);
                        request.setAttribute("attributes", attributes);
                        request.setAttribute("products", products);
                        request.getRequestDispatcher("/productlist.jsp").forward(request, response);
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    }
                    return;
                }
                case "addProduct": {
                    try {
                        final List<CategoryDTO> categories = catalogService.getAllCategories();
                        final List<AttributeDTO> attributes = catalogService.getAllAttributes();
                        request.setAttribute("categories", categories);
                        request.setAttribute("attributes", attributes);
                        request.getRequestDispatcher("/product-edit.jsp").forward(request, response);
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    }
                    return;
                }
                case "viewStatistics": {
                    try {
                        final StatisticsService statisticsService = MilkroadAppContext.getInstance().getStatisticsService();
                        final List<Pair<ProductDTO, Integer>> products = statisticsService.getTopProducts(10);
                        final List<Pair<UserDTO, BigDecimal>> users = statisticsService.getTopCustomers(10);
                        final BigDecimal totalCash = statisticsService.getTotalCash();
                        final Calendar calendar = Calendar.getInstance();
                        final long currentDay = calendar.getTime().getTime();
                        calendar.set(Calendar.DAY_OF_MONTH, 1);
                        final long monthFirstDay = calendar.getTime().getTime();
                        final long sevenDaysAgo = currentDay - 7 * 1000 * 60 * 60 * 24;
                        final BigDecimal totalCashThisMonth = statisticsService.getTotalCashByPeriod(new Date(monthFirstDay), new Date(currentDay));
                        final BigDecimal totalCashLast7Days = statisticsService.getTotalCashByPeriod(new Date(sevenDaysAgo), new Date(currentDay));
                        request.setAttribute("products", products);
                        request.setAttribute("users", users);
                        request.setAttribute("totalCash", totalCash);
                        request.setAttribute("totalCashThisMonth", totalCashThisMonth);
                        request.setAttribute("totalCashLast7Days", totalCashLast7Days);
                        request.getRequestDispatcher("/statistics.jsp").forward(request, response);
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
                case "updateOrder": {
                    final OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setId(Long.valueOf(request.getParameter("orderID")));
                    orderDTO.setPaymentMethod(PaymentMethodEnum.valueOf(request.getParameter("paymentMethod")));
                    orderDTO.setPaymentStatus(PaymentStatusEnum.valueOf(request.getParameter("paymentStatus")));
                    orderDTO.setShippingMethod(ShippingMethodEnum.valueOf(request.getParameter("shippingMethod")));
                    orderDTO.setShippingStatus(ShippingStatusEnum.valueOf(request.getParameter("shippingStatus")));
                    try {
                        MilkroadAppContext.getInstance().getOrderService().updateOrder(orderDTO);
                        response.sendRedirect("/management?action=editOrders");
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    }
                    return;
                }

                case "updateCategory": {
                    if (errors.size() == 0) {
                        final CategoryDTO categoryDTO = new CategoryDTO(
                                Long.valueOf(request.getParameter("categoryID")),
                                request.getParameter("categoryName"),
                                request.getParameter("categoryDescription")
                        );
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().updateCategory(categoryDTO);
                            response.sendRedirect("/management?action=editCategories");
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
                    request.setAttribute("action", "editCategories");
                    doGet(request, response);
                    return;
                }

                case "createCategory": {
                    if (errors.size() == 0) {
                        final CategoryDTO categoryDTO = new CategoryDTO(
                                null,
                                request.getParameter("categoryName"),
                                request.getParameter("categoryDescription")
                        );
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().createCategory(categoryDTO);
                            response.sendRedirect("/management?action=editCategories");
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
                    request.setAttribute("action", "editCategories");
                    doGet(request, response);
                    return;
                }

                case "updateAttribute": {
                    if (errors.size() == 0) {
                        final AttributeDTO attributeDTO = new AttributeDTO(
                                Long.valueOf(request.getParameter("attributeID")),
                                request.getParameter("attributeName"),
                                request.getParameter("attributeDescription")
                        );
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().updateAttribute(attributeDTO);
                            response.sendRedirect("/management?action=editCategories");
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
                    request.setAttribute("action", "editCategories");
                    doGet(request, response);
                    return;
                }

                case "createAttribute": {
                    if (errors.size() == 0) {
                        final AttributeDTO attributeDTO = new AttributeDTO(
                                null,
                                request.getParameter("attributeName"),
                                request.getParameter("attributeDescription")
                        );
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().createAttribute(attributeDTO);
                            response.sendRedirect("/management?action=editCategories");
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
                    request.setAttribute("action", "editCategories");
                    doGet(request, response);
                    return;
                }

                case "updateProduct": {
                    if (errors.size() == 0) {
                        final ProductDTO productDTO = new ProductDTO();
                        productDTO.setArticle(Long.valueOf(request.getParameter("productArticle")));
                        productDTO.setName(request.getParameter("productName"));
                        productDTO.setPrice(new BigDecimal(request.getParameter("productPrice")));
                        productDTO.setCount(Integer.valueOf(request.getParameter("productCount")));
                        productDTO.setDescription(request.getParameter("productDescription"));
                        /*final Long categoryID = Long.valueOf(request.getParameter("productCategoryID"));
                        final String[] parameters = request.getParameterValues("productParameter");
                        try {
                            MilkroadAppContext.getInstance().getCatalogService().updateProduct(productDTO, categoryID, parameters);
                            response.sendRedirect("/management?action=editProducts");
                            return;
                        } catch (final MilkroadServiceException e) {
                            request.setAttribute("message", "DB error! Please, try later");
                            request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                            return;
                        }*/
                    }
                    request.setAttribute("errors", errors);
                    request.setAttribute("action", "editProducts");
                    doGet(request, response);
                    return;
                }

                case "createProduct": {
                    if (errors.size() == 0) {
                        final ProductDTO productDTO = new ProductDTO();
                        productDTO.setName(request.getParameter("productName"));
                        productDTO.setPrice(new BigDecimal(request.getParameter("productPrice")));
                        productDTO.setCount(Integer.valueOf(request.getParameter("productCount")));
                        productDTO.setDescription(request.getParameter("productDescription"));
                        /*final Long categoryID = Long.valueOf(request.getParameter("productCategoryID"));
                        final String[] parameters = request.getParameterValues("productParameter");
                        try {
                            final UserDTO userDTO = AuthUtil.getAuthedUser(request.getSession());
                            MilkroadAppContext.getInstance().getCatalogService().createProduct(userDTO, productDTO, categoryID, parameters);
                            response.sendRedirect("/management?action=editProducts");
                            return;
                        } catch (final MilkroadServiceException e) {
                            request.setAttribute("message", "DB error! Please, try later");
                            request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                            return;
                        }*/
                    }
                    request.setAttribute("errors", errors);
                    request.setAttribute("action", "editProducts");
                    doGet(request, response);
                    return;
                }

                case "addOrEditProduct": {
                    return;
                }
            }
        }
        response.sendRedirect("/");
    }
}
