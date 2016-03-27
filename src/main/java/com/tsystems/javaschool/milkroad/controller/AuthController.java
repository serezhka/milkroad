package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.form.LoginForm;
import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

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
            @ModelAttribute @Valid final LoginForm loginForm,
            final BindingResult bindingResult,
            final HttpServletRequest request) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.size() == 0) {
            final UserDTO user;
            try {
                user = userService.getUserByEmailAndPass(loginForm.getEmail(), loginForm.getPass());
                AuthUtil.authUser(request.getSession(), user);
                return "redirect:/";
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.USER_NOT_EXISTS) {
                    errors.put("email", "User with email not exists");
                } else if (e.getType() == MilkroadServiceException.Type.PASS_INVALID) {
                    errors.put("pass", "Invalid pass");
                } else {
                    request.setAttribute("message", "DB error! Please, try later");
                    return "single-message";
                }
            }
        }
        request.setAttribute("errors", errors);
        request.setAttribute("input", Collections.singletonMap("email", loginForm.getEmail()));
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String processLogout(final HttpServletRequest request) {
        request.getSession().setAttribute("cart", null);
        request.getSession().setAttribute("cartTotal", null);
        AuthUtil.deauthUser(request.getSession());
        return "redirect:/";
    }
}
