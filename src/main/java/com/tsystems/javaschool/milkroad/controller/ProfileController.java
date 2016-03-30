package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.AddressService;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
public class ProfileController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profilePage(final HttpServletRequest request) {
        try {
            //noinspection ConstantConditions
            final String email = SecurityContextHolder.getContext().getAuthentication().getName();
            final UserDTO userDTO = userService.getUserByEmail(email);
            final List<OrderDTO> orderDTOs = orderService.getOrdersByUser(userDTO);
            request.setAttribute("user", userDTO);
            request.setAttribute("orders", orderDTOs);
        } catch (final MilkroadServiceException e) {
            request.setAttribute("message", "DB error! Please, try later");
            return "single-message";
        }
        return "profile";
    }

    @RequestMapping(value = "/profile/edit", method = RequestMethod.POST)
    public String editProfile(
            @ModelAttribute @Valid UserDTO userDTO,
            final BindingResult bindingResult,
            @RequestParam final String pass,
            final HttpServletRequest request) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.size() == 0) {
            //noinspection ConstantConditions
            final String email = SecurityContextHolder.getContext().getAuthentication().getName();
            try {
                userDTO.setId(userService.getUserByEmail(email).getId());
                userDTO = userService.updateUserInfo(userDTO);
                if (!pass.isEmpty()) {
                    userService.updateUserPass(userDTO, pass);
                }
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
    public String editAddress(
            @ModelAttribute @Valid final AddressDTO addressDTO,
            final BindingResult bindingResult,
            final HttpServletRequest request) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.size() == 0) {
            try {
                addressService.updateAddress(addressDTO);
                return "redirect:/profile";
            } catch (final MilkroadServiceException e) {
                request.setAttribute("message", "DB error! Please, try later");
                return "single-message";
            }
        }
        request.setAttribute("errors", errors);
        return profilePage(request);
    }

    @RequestMapping(value = "/profile/addAddress", method = RequestMethod.POST)
    public String addAddress(
            @ModelAttribute @Valid final AddressDTO addressDTO,
            final BindingResult bindingResult,
            final HttpServletRequest request) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.size() == 0) {
            try {
                final String email = SecurityContextHolder.getContext().getAuthentication().getName();
                final UserDTO userDTO = userService.getUserByEmail(email);
                addressService.addAddressToUser(userDTO, addressDTO);
                return "redirect:/profile";
            } catch (final MilkroadServiceException e) {
                request.setAttribute("message", "DB error! Please, try later");
                return "single-message";
            }
        }
        request.setAttribute("errors", errors);
        return profilePage(request);
    }
}
