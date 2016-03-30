package com.tsystems.javaschool.milkroad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
public class AuthController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
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
    }*/

    @RequestMapping("/logoutSuccess")
    public String processLogout(final HttpServletRequest request) {
        request.getSession().setAttribute("cart", null);
        request.getSession().setAttribute("cartTotal", null);
//        AuthUtil.deauthUser(request.getSession());
        return "redirect:/";
    }

    @RequestMapping("/accessDenied")
    public ModelAndView accessDeniedPage() {
        return new ModelAndView("single-message").addObject("message", "Access denied!");
    }

    @RequestMapping("/error")
    public String errorPage() {
        return "single-message";
    }
}
