package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
public class ProfileController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profilePage(final HttpServletRequest request) {
        try {
            //noinspection ConstantConditions
            final UserDTO user = userService.getUserByEmail(AuthUtil.getAuthedUser(request.getSession()).getEmail());
            final List<OrderDTO> orders = orderService.getOrdersByUser(AuthUtil.getAuthedUser(request.getSession()));
            request.setAttribute("user", user);
            request.setAttribute("orders", orders);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "profile";
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public String editProfile(
            @ModelAttribute("errors") final Set<String> errors,
            @ModelAttribute("pass") final String pass,
            @ModelAttribute("birthday") final Date birthday,
            @RequestParam("firstname") final String firstname,
            @RequestParam("lastname") final String lastname,
            final HttpServletRequest request) {

        if (errors.size() == 0) {
            UserDTO userDTO = AuthUtil.getAuthedUser(request.getSession());
            //noinspection ConstantConditions
            userDTO.setFirstName(firstname);
            userDTO.setLastName(lastname);
            userDTO.setBirthday(birthday);
            try {
                userDTO = userService.updateUserInfo(userDTO);
                if (!pass.isEmpty()) {
                    userDTO = userService.updateUserPass(userDTO, pass);
                }
                AuthUtil.authUser(request.getSession(), userDTO);
                return "redirect:/profile";
            } catch (final MilkroadServiceException e) {
                request.setAttribute("message", "DB error! Please, try later");
                return "single-message";
            }
        }
        request.setAttribute("errors", errors);
        return profilePage(request);
    }

    @RequestMapping(value = "/profile/editAddress", method = RequestMethod.POST)
    public String editAddress() {
        return "";
    }

    @ModelAttribute("errors")
    public Set<String> getErrors(final HttpServletRequest request) {
        final Object errors = request.getAttribute("errors");
        //noinspection unchecked
        return (errors == null) ? new HashSet<>() : (Set<String>) errors;
    }

    @ModelAttribute("birthday")
    public Date getBirthday(final HttpServletRequest request) {
        final Object birthday = request.getAttribute("birthday");
        //noinspection unchecked
        return (birthday == null) ? null : (Date) birthday;
    }
}
