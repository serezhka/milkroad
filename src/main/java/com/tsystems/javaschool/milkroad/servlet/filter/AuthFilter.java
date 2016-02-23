package com.tsystems.javaschool.milkroad.servlet.filter;

import com.tsystems.javaschool.milkroad.util.AuthUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Sergey on 23.02.2016.
 */
public class AuthFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpSession session = ((HttpServletRequest) request).getSession();
        if (AuthUtil.isUserAuthed(session)) {
            request.setAttribute(AuthUtil.AUTHED_USER, AuthUtil.getAuthedUser(session));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
