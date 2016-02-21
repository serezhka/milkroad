package com.tsystems.javaschool.milkroad.servlet;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Sergey on 18.02.2016.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String email = request.getParameter("email");
        final String pass = request.getParameter("pass");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("email = " + email + "\n");
            writer.write("pass = " + pass + "\n");
        } catch (final IOException e) {
            LOGGER.error("Login error! email = " + email + ", pass = " + pass);
        }
    }
}
