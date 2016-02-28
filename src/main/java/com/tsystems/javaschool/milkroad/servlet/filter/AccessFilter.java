package com.tsystems.javaschool.milkroad.servlet.filter;

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
        if (!AuthUtil.isUserAuthed(httpRequest.getSession())) {
            final String requestURI = httpRequest.getRequestURI();
            if (requestURI.equals("/profile") || requestURI.equals("/checkout"))
                ((HttpServletResponse) response).sendRedirect("/login");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
