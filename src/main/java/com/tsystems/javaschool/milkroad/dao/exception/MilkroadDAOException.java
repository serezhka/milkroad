package com.tsystems.javaschool.milkroad.dao.exception;

/**
 * Created by Sergey on 10.02.2016.
 */
public class MilkroadDAOException extends Exception {
    public MilkroadDAOException() {
        super();
    }

    public MilkroadDAOException(final String message) {
        super(message);
    }

    public MilkroadDAOException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MilkroadDAOException(final Throwable cause) {
        super(cause);
    }

    protected MilkroadDAOException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
