package com.tsystems.javaschool.milkroad.servlet.filter;

import com.tsystems.javaschool.milkroad.model.UserTypeEnum;
import com.tsystems.javaschool.milkroad.util.AuthUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sergey on 23.02.2016.
 */
public class AccessFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final UserTypeEnum userType = AuthUtil.getAuthedUserType(httpRequest.getSession());
        final String requestURI = httpRequest.getRequestURI();
        // TODO Too hard logic here :(
        if (userType == null) {
            if (!requestURI.equals("/login") && !requestURI.equals("/register")) {
                ((HttpServletResponse) response).sendRedirect("/login");
                return;
            }
        } else {
            if (requestURI.equals("/checkout")) {
                if (userType != UserTypeEnum.CUSTOMER) {
                    request.setAttribute("message", "Access denied");
                    request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                }
            }
            if (requestURI.equals("/management")) {
                if (userType != UserTypeEnum.ADMIN && userType != UserTypeEnum.SELLER) {
                    request.setAttribute("message", "Access denied");
                    request.getRequestDispatcher("/single-message.jsp").forward(request, response);
                }
            }
            if (requestURI.equals("/login") || requestURI.equals("/register")) {
                request.setAttribute("message", "Logout first");
                request.getRequestDispatcher("/single-message.jsp").forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
