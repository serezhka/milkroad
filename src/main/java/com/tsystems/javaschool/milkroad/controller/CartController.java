package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.CatalogService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey on 14.03.2016.
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    private static final Logger LOGGER = Logger.getLogger(CartController.class);

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private String dbErrorMessage;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String cartPage() {
        return "cart";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(
            @ModelAttribute("cart") final Map<ProductDTO, Integer> cart,
            @ModelAttribute("cartTotal") final BigDecimal cartTotal,
            @RequestParam("article") final Long article,
            final HttpServletRequest request) {
        final ProductDTO productDTO;
        try {
            productDTO = catalogService.getProductByArticle(article);
            cart.put(productDTO, cart.getOrDefault(productDTO, 0) + 1);
        } catch (final MilkroadServiceException e) {
            LOGGER.error(e);
            return dbErrorPage(request);
        }
        // TODO Session attribute ?!
        request.getSession().setAttribute("cart", new HashMap<>(cart));
        BigDecimal total = BigDecimal.ZERO;
        for (final ProductDTO product : cart.keySet()) {
            total = total.add(product.getPrice().multiply(new BigDecimal(cart.get(product))));
        }
        request.getSession().setAttribute("cartTotal", (BigDecimal.ZERO.equals(total)) ? null : total);
        request.setAttribute("cartTotal", (BigDecimal.ZERO.equals(total)) ? null : total);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/removeProduct", method = RequestMethod.POST)
    public String removeProduct(
            @ModelAttribute("cart") final Map<ProductDTO, Integer> cart,
            @ModelAttribute("cartTotal") final BigDecimal cartTotal,
            @RequestParam("article") final Long article,
            final HttpServletRequest request) {
        final ProductDTO productDTO;
        try {
            productDTO = catalogService.getProductByArticle(article);
            cart.remove(productDTO);
        } catch (final MilkroadServiceException e) {
            LOGGER.error(e);
            return dbErrorPage(request);
        }
        // TODO Session attribute ?!
        request.getSession().setAttribute("cart", new HashMap<>(cart));
        BigDecimal total = BigDecimal.ZERO;
        for (final ProductDTO product : cart.keySet()) {
            total = total.add(product.getPrice().multiply(new BigDecimal(cart.get(product))));
        }
        request.getSession().setAttribute("cartTotal", (BigDecimal.ZERO.equals(total)) ? null : total);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/removeProductOnce", method = RequestMethod.POST)
    public String removeProductOnce(
            @ModelAttribute("cart") final Map<ProductDTO, Integer> cart,
            @ModelAttribute("cartTotal") final BigDecimal cartTotal,
            @RequestParam("article") final Long article,
            final HttpServletRequest request) {
        final ProductDTO productDTO;
        try {
            productDTO = catalogService.getProductByArticle(article);
            final int count = cart.getOrDefault(productDTO, 0);
            if (count < 2) {
                cart.remove(productDTO);
            } else {
                cart.put(productDTO, cart.get(productDTO) - 1);
            }
        } catch (final MilkroadServiceException e) {
            LOGGER.error(e);
            return dbErrorPage(request);
        }
        // TODO Session attribute ?!
        request.getSession().setAttribute("cart", new HashMap<>(cart));
        BigDecimal total = BigDecimal.ZERO;
        for (final ProductDTO product : cart.keySet()) {
            total = total.add(product.getPrice().multiply(new BigDecimal(cart.get(product))));
        }
        request.getSession().setAttribute("cartTotal", (BigDecimal.ZERO.equals(total)) ? null : total);
        return "redirect:/cart";
    }

    @ModelAttribute("cart")
    public Map<ProductDTO, Integer> getCart(final HttpServletRequest request) {
        final Object cart = request.getSession().getAttribute("cart");
        //noinspection unchecked
        return (cart == null) ? new HashMap<>() : (Map<ProductDTO, Integer>) cart;
    }

    @ModelAttribute("cartTotal")
    public BigDecimal getCartTotal(final HttpServletRequest request) {
        final Object cartTotal = request.getSession().getAttribute("cartTotal");
        //noinspection unchecked
        return (cartTotal == null) ? null : (BigDecimal) cartTotal;
    }

    private String dbErrorPage(final HttpServletRequest request) {
        request.setAttribute("message", dbErrorMessage);
        return "single-message";
    }
}
