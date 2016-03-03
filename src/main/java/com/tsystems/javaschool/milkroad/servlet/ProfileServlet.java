package com.tsystems.javaschool.milkroad.servlet;

import com.tsystems.javaschool.milkroad.MilkroadAppContext;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.OrderService;
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
import java.util.List;
import java.util.Set;

/**
 * Created by Sergey on 23.02.2016.
 */
@WebServlet(name = "ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ProfileServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        // TODO Load orders once per session
        final OrderService orderService = MilkroadAppContext.getInstance().getOrderService();
        final UserService userService = MilkroadAppContext.getInstance().getUserService();
        try {
            //noinspection ConstantConditions
            final UserDTO user = userService.getUserByEmail(AuthUtil.getAuthedUser(request.getSession()).getEmail());
            final List<OrderDTO> orders = orderService.getOrdersByUser(AuthUtil.getAuthedUser(request.getSession()));
            request.setAttribute("user", user);
            request.setAttribute("orders", orders);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            request.getRequestDispatcher("/single-message.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String action = request.getParameter("action");
        if (action != null && !action.isEmpty()) {
            final Set<String> errors;
            final Object errorsObj = request.getAttribute("errors");
            //noinspection unchecked
            errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
            switch (action) {
                case "updateProfile": {
                    if (errors.size() == 0) {
                        UserDTO userDTO = AuthUtil.getAuthedUser(request.getSession());
                        //noinspection ConstantConditions
                        userDTO.setFirstName(request.getParameter("firstname"));
                        userDTO.setLastName(request.getParameter("lastname"));
                        userDTO.setBirthday(Date.valueOf(request.getParameter("birthday")));
                        try {
                            final UserService userService = MilkroadAppContext.getInstance().getUserService();
                            userDTO = userService.updateUserInfo(userDTO);
                            final String pass = request.getParameter("pass");
                            if (!pass.isEmpty()) {
                                userDTO = userService.updateUserPass(userDTO, pass);
                            }
                            AuthUtil.authUser(request.getSession(), userDTO);
                            response.sendRedirect("/profile");
                            return;
                        } catch (final MilkroadServiceException e) {
                            request.setAttribute("message", "DB error! Please, try later");
                            request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                            return;
                        }
                    }
                    request.setAttribute("errors", errors);
                    doGet(request, response);
                }

                case "updateAddress": {
                    if (errors.size() == 0) {
                        final AddressDTO addressDTO = new AddressDTO();
                        addressDTO.setId(Long.valueOf(request.getParameter("addressID")));
                        addressDTO.setCountry(request.getParameter("country"));
                        addressDTO.setCity(request.getParameter("city"));
                        addressDTO.setPostcode(Integer.valueOf(request.getParameter("postcode")));
                        addressDTO.setStreet(request.getParameter("street"));
                        addressDTO.setBuilding(request.getParameter("building"));
                        addressDTO.setApartment(request.getParameter("apartment"));
                        try {
                            MilkroadAppContext.getInstance().getAddressService().updateAddress(addressDTO);
                            response.sendRedirect("/profile");
                            return;
                        } catch (final MilkroadServiceException e) {
                            request.setAttribute("message", "DB error! Please, try later");
                            request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                            return;
                        }
                    }
                    request.setAttribute("errors", errors);
                    doGet(request, response);
                }

                case "createAddress": {
                    if (errors.size() == 0) {
                        final AddressDTO addressDTO = new AddressDTO();
                        addressDTO.setCountry(request.getParameter("country"));
                        addressDTO.setCity(request.getParameter("city"));
                        addressDTO.setPostcode(Integer.valueOf(request.getParameter("postcode")));
                        addressDTO.setStreet(request.getParameter("street"));
                        addressDTO.setBuilding(request.getParameter("building"));
                        addressDTO.setApartment(request.getParameter("apartment"));
                        try {
                            final UserDTO userDTO = AuthUtil.getAuthedUser(request.getSession());
                            MilkroadAppContext.getInstance().getAddressService().addAddressToUser(userDTO, addressDTO);
                            response.sendRedirect("/profile");
                            return;
                        } catch (final MilkroadServiceException e) {
                            request.setAttribute("message", "DB error! Please, try later");
                            request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                            return;
                        }
                    }
                    request.setAttribute("errors", errors);
                    doGet(request, response);
                }
            }
        }
        response.sendRedirect("/profile");
    }
}
