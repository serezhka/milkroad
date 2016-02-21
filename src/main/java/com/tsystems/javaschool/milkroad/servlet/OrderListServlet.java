package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
@WebServlet(name = "OrderListServlet")
public class OrderListServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(OrderListServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            final List<OrderDTO> orders = MilkroadAppContext.getInstance().getOrderService().getAllOrders();
            request.setAttribute("orders", orders);
        } catch (final MilkroadServiceException e) {
            // TODO Add error attr to request
            LOGGER.error("Error while loading orders");
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/orderlist.jsp").forward(request, response);
    }
}
