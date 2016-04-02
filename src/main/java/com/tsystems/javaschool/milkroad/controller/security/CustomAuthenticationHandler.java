package com.tsystems.javaschool.milkroad.controller.security;

import com.tsystems.javaschool.milkroad.dto.UserDTO;
import com.tsystems.javaschool.milkroad.service.UserService;
import com.tsystems.javaschool.milkroad.service.exception.MilkroadServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sergey on 30.03.2016.
 */
@Component
public class CustomAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger LOGGER = Logger.getLogger(CustomAuthenticationHandler.class);
    private static final String AUTHED_USER = "AUTHED_USER";

    @Autowired
    private UserService userService;

    @Autowired
    private String dbErrorMessage;

    public CustomAuthenticationHandler() {
    }

    public CustomAuthenticationHandler(final String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws ServletException, IOException {
        final UserDTO userDTO;
        try {
            userDTO = userService.getUserByEmail(authentication.getName());
        } catch (final MilkroadServiceException e) {
            LOGGER.error(e);
            request.setAttribute("message", dbErrorMessage);
            getRedirectStrategy().sendRedirect(request, response, "/error");
            return;
        }
        request.getSession().setAttribute(AUTHED_USER, userDTO);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
