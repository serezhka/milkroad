package com.tsystems.javaschool.milkroad.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.regex.Pattern;

/**
 * Form data validation methods
 * <p/>
 * Created by Sergey on 23.02.2016.
 */
public class FormDataValidator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Validates string email by pattern EMAIL_PATTERN
     *
     * @param email email to validate
     * @return {@code true} if email is valid, {@code false} otherwise
     */
    public static boolean validateEmail(final String email) {
        return email != null && !email.trim().isEmpty() && pattern.matcher(email).matches() && email.length() < 130;
    }

    /**
     * String name must be not {@code null}, max length is 45 chars
     *
     * @param name name to validate
     * @return {@code true} if name is valid, {@code false} otherwise
     */
    public static boolean validateName(final String name) {
        return name != null && !name.trim().isEmpty() && !(name.length() > 45);
    }

    /**
     * String date must String representation of java.sql.Date format: (yyyy-[m]m-[d]d), not {@code null}
     *
     * @param date date to validate
     * @return {@code true} if Date is valid, {@code false} otherwise
     */
    public static boolean validateDate(final String date) {
        try {
            //noinspection ResultOfMethodCallIgnored
            Date.valueOf(date);
            return true;
        } catch (final IllegalArgumentException ignored) {
        }
        return false;
    }

    /**
     * String password  must be not {@code null}, max length is 45 chars
     *
     * @param pass password to validate
     * @return {@code true} if pass is valid, {@code false} otherwise
     */
    public static boolean validatePass(final String pass) {
        return pass != null && !pass.trim().isEmpty() && !(pass.length() > 45);
    }

    /**
     * String integer must be Integer, not {@code null}
     *
     * @param integer - integer to validate
     * @return {@code true} if integer is valid, {@code false} otherwise
     */
    public static boolean validateInteger(final String integer) {
        try {
            //noinspection ResultOfMethodCallIgnored
            Integer.valueOf(integer);
            return true;
        } catch (final NumberFormatException ignored) {
        }
        return false;
    }

    /**
     * String long must be Long, not {@code null}
     *
     * @param longValue long value to validate
     * @return {@code true} if long is valid, {@code false} otherwise
     */
    public static boolean validateLong(final String longValue) {
        try {
            //noinspection ResultOfMethodCallIgnored
            Long.valueOf(longValue);
            return true;
        } catch (final NumberFormatException ignored) {
        }
        return false;
    }

    /**
     * String bigDecimal must be BigDecimal, not {@code null}
     *
     * @param bigDecimal BigDecimal value to validate
     * @return {@code true} if bigDecimal is valid, {@code false} otherwise
     */
    public static boolean validateBigDecimal(final String bigDecimal) {
        try {
            new BigDecimal(bigDecimal);
            return true;
        } catch (final NumberFormatException | NullPointerException ignored) {
        }
        return false;
    }

    /**
     * Chacks if enumString is one of T enum values
     *
     * @param clazz      enum class
     * @param enumString enum value to validate
     * @param <T>        enum class extends Enum
     * @return {@code true} if enumString is one of T values, {@code false} otherwise
     */
    public static <T extends Enum<T>> boolean validateEnum(final Class<T> clazz, final String enumString) {
        try {
            T.valueOf(clazz, enumString);
            return true;
        } catch (IllegalArgumentException | NullPointerException ignored) {
        }
        return false;
    }
}
