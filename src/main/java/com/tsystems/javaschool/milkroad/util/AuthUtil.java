package com.tsystems.javaschool.milkroad.util;

import com.tsystems.javaschool.milkroad.dto.UserDTO;

import javax.servlet.http.HttpSession;

/**
 * Created by Sergey on 22.02.2016.
 */
public class AuthUtil {
    public static final String AUTHED_USER = "AUTHED_USER";

    /**
     * @param httpSession - session
     * @return {@code true} if session has authed user, {@code false} - otherwise
     */
    public static boolean isUserAuthed(final HttpSession httpSession) {
        return getAuthedUser(httpSession) != null;
    }

    /**
     * Sets user authed for current session
     *
     * @param httpSession - session
     * @param user        - authenticating user
     */
    public static void authUser(final HttpSession httpSession, final UserDTO user) {
        httpSession.setAttribute(AUTHED_USER, user);
    }

    /**
     * Deauth user for current session
     *
     * @param httpSession - session
     */
    public static void deauthUser(final HttpSession httpSession) {
        httpSession.setAttribute(AUTHED_USER, null);
    }

    /**
     * @param httpSession - session
     * @return UserDTO - authed user or {@code null} if there is no authed user
     */
    public static UserDTO getAuthedUser(final HttpSession httpSession) {
        final Object user = httpSession.getAttribute(AUTHED_USER);
        return (user != null && user instanceof UserDTO) ? (UserDTO) user : null;
    }
}
