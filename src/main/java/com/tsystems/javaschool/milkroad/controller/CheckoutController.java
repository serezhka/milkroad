package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.model.PaymentMethodEnum;
import com.tsystems.javaschool.milkroad.model.ShippingMethodEnum;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sergey on 15.03.2016.
 */
@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkoutPage() {
        return "checkout";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String createOrder(
            @ModelAttribute("errors") final Set<String> errors,
            final HttpServletRequest request) {

        if (errors.size() == 0) {
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
                return "single-message";
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.PRODUCT_NOT_ENOUGH) {
                    request.setAttribute("message", "Some products in Your cart are missing in our storage");
                    return "single-message";
                }
                request.setAttribute("message", "DB error! Please, try later");
                return "single-message";
            }
        }
        return "checkout";
    }

    @ModelAttribute("errors")
    public Set<String> getErrors(final HttpServletRequest request) {
        final Object errors = request.getAttribute("errors");
        //noinspection unchecked
        return (errors == null) ? new HashSet<>() : (Set<String>) errors;
    }
}
