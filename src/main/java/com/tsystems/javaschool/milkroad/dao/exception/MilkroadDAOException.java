package com.tsystems.javaschool.milkroad.dao.exception;

/**
 * Created by Sergey on 10.02.2016.
 */
public class MilkroadDAOException extends Exception {
    private final DAOErrorType daoErrorType;

    public MilkroadDAOException(final String message, final DAOErrorType daoErrorType) {
        super(message);
        this.daoErrorType = daoErrorType;
    }

    public MilkroadDAOException(final Throwable cause, final DAOErrorType daoErrorType) {
        super(cause);
        this.daoErrorType = daoErrorType;
    }

    public DAOErrorType getDaoErrorType() {
        return daoErrorType;
    }
}
