package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
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
 * Created by Sergey on 13.02.2016.
 */
@WebServlet(name = "UserListServlet")
public class UserListServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UserListServlet.class);

    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            final List<UserDTO> users = MilkroadAppContext.getInstance().getUserService().getAllUsers();
            request.setAttribute("users", users);
        } catch (final MilkroadServiceException e) {
            // TODO Add error attr to request
            LOGGER.error("Error while loading users");
            throw new RuntimeException(e);
        }
        request.getRequestDispatcher("/userlist.jsp").forward(request, response);
    }
}
