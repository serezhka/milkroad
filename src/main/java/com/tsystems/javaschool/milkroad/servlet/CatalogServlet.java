package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.CategoryDTO;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
import com.tsystems.javaschool.milkroad.service.CatalogService;
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
 * Created by Sergey on 24.02.2016.
 */
@WebServlet(name = "CatalogServlet")
public class CatalogServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CatalogServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final CatalogService catalogService = MilkroadAppContext.getInstance().getCatalogService();

        // TODO Load categories one time per session?
        /* Load all categories for Menu */
        try {
            final List<CategoryDTO> categories = catalogService.getAllCategories();
            request.setAttribute("categories", categories);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            request.getRequestDispatcher("/single-message.jsp").forward(request, response);
            return;
        }

        /* Load product info */
        final String article = request.getParameter("article");
        if (article != null && !article.isEmpty()) {
            try {
                request.setAttribute("product", catalogService.getProductByArticle(article));
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.PRODUCT_NOT_EXISTS) {
                    request.setAttribute("message", "Product with article = " + article + " does not exist");
                    request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    return;
                } else {
                    request.setAttribute("message", "DB error! Please, try later");
                    request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    return;
                }
            }
        } else {

             /* Load products by category (or all, if category not specified) */
            final String category = request.getParameter("category");
            final List<ProductDTO> products;
            try {
                if (category != null && !category.isEmpty()) {
                    products = catalogService.getAllProductsByCategory(category);
                } else {
                    products = catalogService.getAllProducts();
                }
                request.setAttribute("products", products);
            } catch (final MilkroadServiceException e) {
                request.setAttribute("message", "DB error! Please, try later");
                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher("/catalog.jsp").forward(request, response);
    }
}
