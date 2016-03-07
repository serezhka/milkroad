package com.tsystems.javaschool.milkroad.service.exception;

/**
 * Created by Sergey on 13.02.2016.
 */
public class MilkroadServiceException extends Exception {
    public enum Type {
        USER_EMAIL_ALREADY_EXISTS,
        PASS_INVALID,
        USER_NOT_EXISTS,
        DAO_ERROR,
        PASS_UTILS_ERROR,
        PRODUCT_NOT_EXISTS,
        CATEGORY_ALREADY_EXISTS,
        ATTRIBUTE_ALREADY_EXISTS,
        ATTRIBUTE_NOT_EXISTS,
        PRODUCT_NOT_ENOUGH,
        ADDRESS_NOT_EXISTS,
        CATEGORY_NOT_EXISTS,
        UNKNOWN_ERROR
    }

    private final Type type;

    public MilkroadServiceException(final Type type) {
        this.type = type;
    }

    public MilkroadServiceException(final String message, final Type type) {
        super(message);
        this.type = type;
    }

    public MilkroadServiceException(final Throwable cause, final Type type) {
        super(cause);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
