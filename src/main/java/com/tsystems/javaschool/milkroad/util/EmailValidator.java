package com.tsystems.javaschool.milkroad.util;

import java.util.regex.Pattern;

/**
 * Created by Sergey on 23.02.2016.
 */
public class EmailValidator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * @param email - e-mail
     * @return {@code true} if email is valid, {@code false} - otherwise
     */
    public static boolean validate(final String email) {
        return email != null && !email.trim().isEmpty() && pattern.matcher(email).matches() && email.length() < 130;
    }
}
