package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(final HttpServletRequest request) {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegister(
            @ModelAttribute @Valid final UserDTO userDTO,
            final BindingResult bindingResult,
            @ModelAttribute final String pass) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.size() == 0) {
            try {
                userService.addNewUser(userDTO, pass);
                return new ModelAndView("single-message").addObject("message", "Registration successful");
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.USER_EMAIL_ALREADY_EXISTS) {
                    errors.put("email", "Email is already used");
                } else {
                    return new ModelAndView("single-message").addObject("message", "DB error! Please, try later");
                }
            }
        }
        return new ModelAndView("register")
                .addObject("input", userDTO)
                .addObject("errors", errors);
    }
}
