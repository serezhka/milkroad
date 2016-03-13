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
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(final HttpServletRequest request) {
        if (!AuthUtil.isUserAuthed(request.getSession())) {
            return "register";
        }
        request.setAttribute("message", "Logout first");
        return "single-message";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(
            @ModelAttribute("errors") final Set<String> errors,
            @ModelAttribute("birthday") final Date birthday,
            @RequestParam("firstname") final String firstname,
            @RequestParam("lastname") final String lastname,
            @RequestParam("email") final String email,
            @RequestParam("pass") final String pass,
            final HttpServletRequest request) {

        if (errors.size() == 0) {
            final UserDTO userDTO = new UserDTO(
                    null,
                    firstname,
                    lastname,
                    birthday,
                    email,
                    null);
            try {
                userService.addNewUser(userDTO, pass);
                request.setAttribute("message", "Registration successful!");
                return "single-message";
            } catch (final MilkroadServiceException e) {
                if (e.getType() == MilkroadServiceException.Type.USER_EMAIL_ALREADY_EXISTS) {
                    errors.add("USER_EMAIL_ALREADY_EXISTS");
                } else {
                    request.setAttribute("message", "DB error! Please, try later");
                    return "single-message";
                }
            }
        }
        request.setAttribute("errors", errors);
        return "register";
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
