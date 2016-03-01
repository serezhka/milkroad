package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.model.PaymentMethodEnum;
import com.tsystems.javaschool.milkroad.model.PaymentStatusEnum;
import com.tsystems.javaschool.milkroad.model.ShippingMethodEnum;
import com.tsystems.javaschool.milkroad.model.ShippingStatusEnum;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sergey on 29.02.2016.
 */
@WebServlet(name = "ManagementServlet")
public class ManagementServlet extends HttpServlet {
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
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

            }
        }
        response.sendRedirect("/");
    }

    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
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
                        //response.sendRedirect("/management?action=viewOrders");
                        request.setAttribute("message", "Order info update successful!");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
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
}
