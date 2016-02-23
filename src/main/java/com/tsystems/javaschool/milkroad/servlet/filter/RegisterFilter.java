package com.tsystems.javaschool.milkroad.servlet.filter;

import com.tsystems.javaschool.milkroad.util.EmailValidator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sergey on 21.02.2016.
 */
public class RegisterFilter implements Filter {
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
            final String firstName = request.getParameter("firstname");
            if (firstName == null || firstName.trim().isEmpty() || firstName.length() > 45) {
                errors.add("FIRST_NAME_ERROR");
            }
            final String lastName = request.getParameter("lastname");
            if (lastName == null || lastName.trim().isEmpty() || lastName.length() > 45) {
                errors.add("LAST_NAME_ERROR");
            }
            Date birthday = null;
            try {
                birthday = Date.valueOf(request.getParameter("birthday"));
            } catch (final IllegalArgumentException ignored) {
                errors.add("DATE_ERROR");
            }
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
            input.put("firstname", firstName);
            input.put("lastname", lastName);
            input.put("birthday", (birthday == null) ? "" : birthday.toString());
            input.put("email", email);
            request.setAttribute("input", input);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
