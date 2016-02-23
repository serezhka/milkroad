package com.tsystems.javaschool.milkroad.servlet.filter;

import com.tsystems.javaschool.milkroad.util.FormDataValidator;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sergey on 23.02.2016.
 */
public class FormDataFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String formName = request.getParameter("formName");
        if (formName != null && !formName.trim().isEmpty()) {
            final Set<String> errors;
            final Object errorsObj = request.getAttribute("errors");
            //noinspection unchecked
            errors = (errorsObj == null) ? new HashSet<>() : (Set<String>) errorsObj;
            final HashMap<String, String> input = new HashMap<>();
            switch (formName) {
                case "registerForm": {
                    final String firstName = request.getParameter("firstname");
                    if (!FormDataValidator.validateName(firstName)) {
                        errors.add("FIRST_NAME_ERROR");
                    }
                    final String lastName = request.getParameter("lastname");
                    if (!FormDataValidator.validateName(lastName)) {
                        errors.add("LAST_NAME_ERROR");
                    }
                    final String birthday = request.getParameter("birthday");
                    if (!FormDataValidator.validateDate(birthday)) {
                        errors.add("DATE_ERROR");
                    }
                    final String email = request.getParameter("email");
                    if (!FormDataValidator.validateEmail(email)) {
                        errors.add("EMAIL_ERROR");
                    }
                    final String pass = request.getParameter("pass");
                    if (!FormDataValidator.validatePass(pass)) {
                        errors.add("PASS_ERROR");
                    }
                    input.put("firstname", firstName);
                    input.put("lastname", lastName);
                    input.put("birthday", birthday);
                    input.put("email", email);
                    break;
                }

                case "loginForm": {
                    final String email = request.getParameter("email");
                    if (!FormDataValidator.validateEmail(email)) {
                        errors.add("EMAIL_ERROR");
                    }
                    final String pass = request.getParameter("pass");
                    if (!FormDataValidator.validatePass(pass)) {
                        errors.add("PASS_ERROR");
                    }
                    input.put("email", email);
                    break;
                }

                case "profileUpdateForm": {
                    final String firstName = request.getParameter("firstname");
                    if (!FormDataValidator.validateName(firstName)) {
                        errors.add("FIRST_NAME_ERROR");
                    }
                    final String lastName = request.getParameter("lastname");
                    if (!FormDataValidator.validateName(lastName)) {
                        errors.add("LAST_NAME_ERROR");
                    }
                    final String birthday = request.getParameter("birthday");
                    if (!FormDataValidator.validateDate(birthday)) {
                        errors.add("DATE_ERROR");
                    }
                    final String pass = request.getParameter("pass");
                    if (!(pass != null && pass.isEmpty()) && !FormDataValidator.validatePass(pass)) {
                        errors.add("PASS_ERROR");
                    }
                    input.put("firstname", firstName);
                    input.put("lastname", lastName);
                    input.put("birthday", birthday);
                    break;
                }
            }
            request.setAttribute("input", input);
            request.setAttribute("errors", errors);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
