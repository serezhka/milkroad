package com.tsystems.javaschool.milkroad.service.exception;

/**
 * Created by Sergey on 13.02.2016.
 */
public class MilkroadServiceException extends Exception {
    private final ServiceExceptionType serviceExceptionType;

    public MilkroadServiceException(final String message, final ServiceExceptionType serviceExceptionType) {
        super(message);
        this.serviceExceptionType = serviceExceptionType;
    }

    public MilkroadServiceException(final Throwable cause, final ServiceExceptionType serviceExceptionType) {
        super(cause);
        this.serviceExceptionType = serviceExceptionType;
    }

    public ServiceExceptionType getServiceExceptionType() {
        return serviceExceptionType;
    }
}
