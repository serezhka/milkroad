package com.tsystems.javaschool.milkroad.util;

import java.sql.Date;
import java.util.regex.Pattern;

/**
 * Created by Sergey on 23.02.2016.
 */
public class FormDataValidator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean validateEmail(final String email) {
        return email != null && !email.trim().isEmpty() && pattern.matcher(email).matches() && email.length() < 130;
    }

    public static boolean validateName(final String name) {
        return name != null && !name.trim().isEmpty() && !(name.length() > 45);
    }

    public static boolean validateDate(final String date) {
        try {
            Date.valueOf(date);
            return true;
        } catch (final IllegalArgumentException ignored) {
        }
        return false;
    }

    public static boolean validatePass(final String pass) {
        return pass != null && !pass.trim().isEmpty() && !(pass.length() > 45);
    }
}
