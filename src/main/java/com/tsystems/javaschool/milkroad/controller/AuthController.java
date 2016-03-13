package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.dto.UserDTO;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLogin(
            @ModelAttribute("errors") final Set<String> errors,
            @RequestParam("email") final String email,
            @RequestParam("pass") final String pass,
            final HttpServletRequest request) {

        if (errors.size() == 0) {
            final UserDTO user;
            try {
                user = userService.getUserByEmailAndPass(email, pass);
                AuthUtil.authUser(request.getSession(), user);
                return "redirect:/";
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.USER_NOT_EXISTS) {
                    errors.add("USER_NOT_EXISTS");
                } else if (e.getType() == MilkroadServiceException.Type.PASS_INVALID) {
                    errors.add("PASS_INVALID");
                } else {
                    request.setAttribute("message", "DB error! Please, try later");
                    return "single-message";
                }
            }
        }
        request.setAttribute("errors", errors);
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String processLogout(final HttpServletRequest request) {
        request.getSession().setAttribute("cart", null);
        request.getSession().setAttribute("cartTotal", null);
        AuthUtil.deauthUser(request.getSession());
        return "index";
    }

    @ModelAttribute("errors")
    public Set<String> getErrors(final HttpServletRequest request) {
        final Object errors = request.getAttribute("errors");
        //noinspection unchecked
        return (errors == null) ? new HashSet<>() : (Set<String>) errors;
    }
}
