package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.AuthUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
        final Set<String> errors;
        final Object errorsObj = request.getAttribute("errors");
        //noinspection unchecked
        errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
        if (errors.size() == 0) {
            final String email = request.getParameter("email");
            final String pass = request.getParameter("pass");
            final UserDTO user;
            try {
                user = MilkroadAppContext.getInstance().getUserService().getUserByEmailAndPass(email, pass);
                AuthUtil.authUser(request.getSession(), user);
                request.setAttribute("message", "Authentication successful! Welcome, " + user.getFirstName());
                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                return;
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.USER_NOT_EXISTS) {
                    errors.add("USER_NOT_EXISTS");
                } else if (e.getType() == MilkroadServiceException.Type.PASS_INVALID) {
                    errors.add("PASS_INVALID");
                } else {
                    // TODO Error page ???
                    errors.add("UNKNOWN_ERROR");
                    throw new RuntimeException(e);
                }
            }
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
