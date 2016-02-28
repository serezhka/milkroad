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
        final Map<ProductDTO, Integer> cart;
        final Object cartObj = request.getSession().getAttribute("cart");
        //noinspection unchecked
        cart = (cartObj == null) ? new HashMap<>() : (Map<ProductDTO, Integer>) cartObj;
        final String addArticle = request.getParameter("article");
        final String removeArticle = request.getParameter("remove");
        if ((addArticle != null && !addArticle.isEmpty())
                || (removeArticle != null && !removeArticle.isEmpty())) {
            try {
                final ProductDTO productDTO;
                if (addArticle != null && !addArticle.isEmpty()) {
                    productDTO = MilkroadAppContext.getInstance().getCatalogService().getProductByArticle(addArticle);
                    cart.put(productDTO, cart.getOrDefault(productDTO, 0) + 1);
                } else if (removeArticle != null && !removeArticle.isEmpty()) {
                    productDTO = MilkroadAppContext.getInstance().getCatalogService().getProductByArticle(removeArticle);
                    cart.remove(productDTO);
                }
                request.getSession().setAttribute("cart", cart);
                BigDecimal total = BigDecimal.ZERO;
                for (final ProductDTO product : cart.keySet()) {
                    total = total.add(product.getPrice().multiply(new BigDecimal(cart.get(product))));
                }
                request.getSession().setAttribute("cartTotal", total);
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.PRODUCT_NOT_EXISTS) {
                    request.setAttribute("message", "Product with article = " + addArticle + " does not exist");
                    request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                } else {
                    request.setAttribute("message", "DB error! Please, try later");
                    request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    return;
                }
            }
        }
    }
}
