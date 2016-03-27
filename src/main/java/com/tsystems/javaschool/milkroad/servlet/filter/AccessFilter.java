package com.tsystems.javaschool.milkroad.servlet.filter;

import com.tsystems.javaschool.milkroad.model.UserTypeEnum;
import com.tsystems.javaschool.milkroad.util.AuthUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.tsystems.javaschool.milkroad.model.UserTypeEnum.*;

/**
 * Created by Sergey on 23.02.2016.
 */
public class AccessFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final UserTypeEnum userType = AuthUtil.getAuthedUserType(httpRequest.getSession());
        final String requestURI = httpRequest.getRequestURI();

        if (requestURI.startsWith("/management")) {
            if (userType != ADMIN && userType != SELLER) {
                messagePage("Access denied", request, response);
                return;
            } else if (userType == null) {
                ((HttpServletResponse) response).sendRedirect("/login");
                return;
            }
        }

        if (requestURI.startsWith("/checkout")) {
            if (userType == null) {
                ((HttpServletResponse) response).sendRedirect("/login");
                return;
            } else if (userType != CUSTOMER) {
                messagePage("Access denied", request, response);
                return;
            }
        }

        if (requestURI.startsWith("/login") || requestURI.startsWith("/register")) {
            if (userType != null) {
                messagePage("Logout first", request, response);
                return;
            }
        }

        if (requestURI.startsWith("/profile")) {
            if (userType == null) {
                ((HttpServletResponse) response).sendRedirect("/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private void messagePage(final String message, final ServletRequest request, final ServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/jsp/single-message.jsp").forward(request, response);
    }
}
