package com.tsystems.javaschool.milkroad.dao.exception;

/**
 * Created by Sergey on 10.02.2016.
 */
public class MilkroadDAOException extends Exception {
    public enum Type {
        PERSIST_ERROR,
        MERGE_ERROR,
        REMOVE_ERROR,
        FIND_ERROR
    }

    private final Type type;

    public MilkroadDAOException(final String message, final Type type) {
        super(message);
        this.type = type;
    }

    public MilkroadDAOException(final Throwable cause, final Type type) {
        super(cause);
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
