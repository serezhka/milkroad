package com.tsystems.javaschool.milkroad.servlet.filter;

import com.tsystems.javaschool.milkroad.util.EmailValidator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sergey on 23.02.2016.
 */
public class LoginFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod().equalsIgnoreCase("POST")) {
            final Set<String> errors;
            final Object errorsObj = request.getAttribute("errors");
            //noinspection unchecked
            errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
            final String email = request.getParameter("email");
            if (!EmailValidator.validate(email)) {
                errors.add("EMAIL_ERROR");
            }
            final String pass = request.getParameter("pass");
            if (pass == null || pass.trim().isEmpty()) {
                errors.add("PASS_ERROR");
            }
            request.setAttribute("errors", errors);
            final HashMap<String, String> input = new HashMap<>();
            input.put("email", email);
            request.setAttribute("input", input);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
