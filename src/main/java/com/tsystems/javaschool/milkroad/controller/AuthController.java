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

    @RequestMapping("/logoutSuccess")
    public String processLogout(final HttpServletRequest request) {
        request.getSession().setAttribute("cart", null);
        request.getSession().setAttribute("cartTotal", null);
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
