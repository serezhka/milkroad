package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private String dbErrorMessage;

    @RequestMapping(method = RequestMethod.GET)
    public String registerPage() {
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView processRegister(
            @ModelAttribute @Valid final UserDTO userDTO,
            final BindingResult bindingResult,
            @RequestParam final String pass) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.size() == 0) {
            try {
                userService.addNewUser(userDTO, pass);
                return new ModelAndView("single-message").addObject("message", "Registration successful");
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.USER_EMAIL_ALREADY_EXISTS) {
                    LOGGER.info(e);
                    errors.put("email", "Email is already used");
                } else {
                    LOGGER.error(e);
                    return new ModelAndView("single-message").addObject("message", dbErrorMessage);
                }
            }
        }
        return new ModelAndView("register")
                .addObject("input", userDTO)
                .addObject("errors", errors);
    }
}
