package com.tsystems.javaschool.milkroad.controller;

import com.tsystems.javaschool.milkroad.controller.util.ControllerUtils;
import com.tsystems.javaschool.milkroad.dto.AddressDTO;
import com.tsystems.javaschool.milkroad.dto.OrderDTO;
import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.AddressService;
import com.tsystems.javaschool.milkroad.service.OrderService;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import com.tsystems.javaschool.milkroad.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
            @ModelAttribute @Valid UserDTO userDTO,
            final BindingResult bindingResult,
            @RequestParam final String pass,
            final HttpServletRequest request) {
        final Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        if (errors.size() == 0) {
            //noinspection ConstantConditions
            userDTO.setId(AuthUtil.getAuthedUser(request.getSession()).getId());
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
                final UserDTO userDTO = AuthUtil.getAuthedUser(request.getSession());
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
