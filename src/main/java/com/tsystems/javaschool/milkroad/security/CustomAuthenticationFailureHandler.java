package com.tsystems.javaschool.milkroad.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sergey on 04.04.2016.
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger LOGGER = Logger.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException, ServletException {
        LOGGER.info("Auth failed. Email: " + request.getParameter("email"));
        super.onAuthenticationFailure(request, response, exception);
    }
}
