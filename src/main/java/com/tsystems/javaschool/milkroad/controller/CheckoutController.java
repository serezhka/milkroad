package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by Sergey on 15.03.2016.
 */
@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    private static final Logger LOGGER = Logger.getLogger(CheckoutController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private String dbErrorMessage;

    @RequestMapping(method = RequestMethod.GET)
    public String checkoutPage() {
        return "checkout";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createOrder(
            @ModelAttribute @Valid final OrderDTO orderDTO,
            final BindingResult bindingResult,
            final HttpServletRequest request) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.size() == 0) {
            orderDTO.setTotalPrice((BigDecimal) request.getSession().getAttribute("cartTotal"));
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
                    LOGGER.info(e);
                    return singleMessagePage(request, "Some products in Your cart are missing in our storage");
                }
                LOGGER.error(e);
                return singleMessagePage(request, dbErrorMessage);
            }
        }
        return "checkout";
    }

    private String singleMessagePage(final HttpServletRequest request, final String message) {
        request.setAttribute("message", message);
        return "single-message";
    }
}
