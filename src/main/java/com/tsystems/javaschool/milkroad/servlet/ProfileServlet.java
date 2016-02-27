package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.AuthUtil;
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
 * Created by Sergey on 23.02.2016.
 */
@WebServlet(name = "ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ProfileServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final Set<String> errors;
        final Object errorsObj = request.getAttribute("errors");
        //noinspection unchecked
        errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
        if (errors.size() == 0) {
            final UserService userService = MilkroadAppContext.getInstance().getUserService();
            UserDTO userDTO = AuthUtil.getAuthedUser(request.getSession());
            final String action = request.getParameter("formName");
            // TODO This is not beautiful :(
            if (action.equals("profileUpdateForm")) {
                //noinspection ConstantConditions
                userDTO.setFirstName(request.getParameter("firstname"));
                userDTO.setLastName(request.getParameter("lastname"));
                userDTO.setBirthday(Date.valueOf(request.getParameter("birthday")));
                try {
                    userDTO = userService.updateUserInfo(userDTO);
                    final String pass = request.getParameter("pass");
                    if (!pass.isEmpty()) {
                        userDTO = userService.updateUserPass(userDTO, pass);
                    }
                    AuthUtil.authUser(request.getSession(), userDTO);
                    request.setAttribute("message", "Profile update successful!");
                    request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    return;
                } catch (final MilkroadServiceException e) {
                    // TODO Error page ???
                    errors.add("UNKNOWN_ERROR");
                    throw new RuntimeException(e);
                }
            } else if (action.equals("addAddressForm")) {
                final AddressDTO addressDTO = new AddressDTO(
                        request.getParameter("country"),
                        request.getParameter("city"),
                        Integer.valueOf(request.getParameter("postcode")),
                        request.getParameter("street"),
                        request.getParameter("building"),
                        request.getParameter("apartment")
                );
                try {
                    userDTO = userService.addAddressToUser(userDTO, addressDTO);
                    AuthUtil.authUser(request.getSession(), userDTO);
                    request.setAttribute("message", "Address add successfuil!");
                    request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                    return;
                } catch (final MilkroadServiceException e) {
                    // TODO Error page ???
                    errors.add("UNKNOWN_ERROR");
                    throw new RuntimeException(e);
                }
            }
        }
        request.setAttribute("errors", errors);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}
