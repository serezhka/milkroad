package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey on 25.02.2016.
 */
@WebServlet(name = "CartServlet")
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            final Map<ProductDTO, Integer> cart;
            final Object cartObj = request.getSession().getAttribute("cart");
            //noinspection unchecked
            cart = (cartObj == null) ? new HashMap<>() : (Map<ProductDTO, Integer>) cartObj;
            switch (action) {
                case "addProduct": {
                    final Long article = Long.valueOf(request.getParameter("article"));
                    final ProductDTO productDTO;
                    try {
                        productDTO = MilkroadAppContext.getInstance().getCatalogService().getProductByArticle(article);
                        cart.put(productDTO, cart.getOrDefault(productDTO, 0) + 1);
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                        return;
                    }
                    break;
                }

                case "removeProduct": {
                    final Long article = Long.valueOf(request.getParameter("article"));
                    final ProductDTO productDTO;
                    try {
                        productDTO = MilkroadAppContext.getInstance().getCatalogService().getProductByArticle(article);
                        cart.remove(productDTO);
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                        return;
                    }
                    break;
                }

                case "removeProductOnce": {
                    final Long article = Long.valueOf(request.getParameter("article"));
                    final ProductDTO productDTO;
                    try {
                        productDTO = MilkroadAppContext.getInstance().getCatalogService().getProductByArticle(article);
                        final int count = cart.getOrDefault(productDTO, 0);
                        if (count < 1) {
                            cart.remove(productDTO);
                        } else {
                            cart.put(productDTO, cart.get(productDTO) - 1);
                        }
                    } catch (final MilkroadServiceException e) {
                        request.setAttribute("message", "DB error! Please, try later");
                        request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                        return;
                    }
                    break;
                }
            }
            request.getSession().setAttribute("cart", cart);
            BigDecimal total = BigDecimal.ZERO;
            for (final ProductDTO product : cart.keySet()) {
                total = total.add(product.getPrice().multiply(new BigDecimal(cart.get(product))));
            }
            request.getSession().setAttribute("cartTotal", total);
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("/cart");
    }
}
