package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.PaymentMethodEnum;
import com.tsystems.javaschool.milkroad.model.ShippingMethodEnum;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.AuthUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sergey on 27.02.2016.
 */
@WebServlet(name = "CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final Set<String> errors;
        final Object errorsObj = request.getAttribute("errors");
        //noinspection unchecked
        errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
        if (errors.size() == 0) {
            final OrderService orderService = MilkroadAppContext.getInstance().getOrderService();
            final UserDTO userDTO = AuthUtil.getAuthedUser(request.getSession());
            final AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(Long.valueOf(request.getParameter("address")));
            final PaymentMethodEnum paymentMethod = PaymentMethodEnum.valueOf(request.getParameter("payment"));
            final ShippingMethodEnum shippingMethod = ShippingMethodEnum.valueOf(request.getParameter("shipping"));
            final BigDecimal totalPrice = (BigDecimal) request.getSession().getAttribute("cartTotal");
            final OrderDTO orderDTO = new OrderDTO();
            orderDTO.setAddress(addressDTO);
            orderDTO.setCustomer(userDTO);
            orderDTO.setPaymentMethod(paymentMethod);
            orderDTO.setShippingMethod(shippingMethod);
            orderDTO.setTotalPrice(totalPrice);
            //noinspection unchecked
            final Map<ProductDTO, Integer> cart = (Map<ProductDTO, Integer>) request.getSession().getAttribute("cart");
            for (final ProductDTO productDTO : cart.keySet()) {
                final BigDecimal price = productDTO.getPrice();
                final int count = cart.get(productDTO);
                orderDTO.addDetail(productDTO, count, price.multiply(new BigDecimal(count)));
            }
            try {
                orderService.createOrder(orderDTO);
                request.getSession().setAttribute("cart", null);
                request.getSession().setAttribute("cartTotal", null);
                request.setAttribute("message", "Order successful!");
                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                return;
            } catch (final MilkroadServiceException e) {
                request.setAttribute("message", "DB error! Please, try later");
                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
    }
}
