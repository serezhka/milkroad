package com.tsystems.javaschool.milkroad.service.exception;

/**
 * Created by Sergey on 13.02.2016.
 */
public class MilkroadServiceException extends Exception {
    public MilkroadServiceException() {
        super();
    }

    public MilkroadServiceException(final String message) {
        super(message);
    }

    public MilkroadServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MilkroadServiceException(final Throwable cause) {
        super(cause);
    }

    protected MilkroadServiceException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
