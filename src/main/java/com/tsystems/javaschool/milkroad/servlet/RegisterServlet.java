package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.service.exception.ServiceExceptionType;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sergey on 18.02.2016.
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(RegisterServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final Set<String> errors;
        final Object errorsObj = request.getAttribute("errors");
        //noinspection unchecked
        errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
        if (errors.size() == 0) {
            final UserService userService = MilkroadAppContext.getInstance().getUserService();
            final UserDTO userDTO = new UserDTO(
                    request.getParameter("firstname"),
                    request.getParameter("lastname"),
                    Date.valueOf(request.getParameter("birthday")),
                    request.getParameter("email"));
            try {
                userService.addNewUser(userDTO, request.getParameter("pass"));
                request.setAttribute("message", "Registration successful!");
                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                return;
            } catch (final MilkroadServiceException e) {
                if (e.getServiceExceptionType() == ServiceExceptionType.USER_EMAIL_ALREADY_EXISTS) {
                    errors.add("USER_EMAIL_ALREADY_EXISTS");
                } else {
                    // TODO Error page ???
                    errors.add("UNKNOWN_ERROR");
                    e.printStackTrace();
                }
            }
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }
}
