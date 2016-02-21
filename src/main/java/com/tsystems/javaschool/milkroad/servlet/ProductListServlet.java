package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.ProductDTO;
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
@WebServlet(name = "ProductListServlet")
public class ProductListServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserListServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            final List<ProductDTO> products = MilkroadAppContext.getInstance().getProductService().getAllProducts();
            request.setAttribute("products", products);
        } catch (final MilkroadServiceException e) {
            // TODO Add error attr to request
            LOGGER.error("Error while loading products");
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/productlist.jsp").forward(request, response);
    }
}
