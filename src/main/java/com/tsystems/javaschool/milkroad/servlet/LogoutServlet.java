package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.util.AuthUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sergey on 23.02.2016.
 */
@Deprecated
@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("cart", null);
        request.getSession().setAttribute("cartTotal", null);
        AuthUtil.deauthUser(request.getSession());
        response.sendRedirect("/");
    }
}
