package com.tsystems.javaschool.milkroad.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sergey on 24.03.2016.
 */
@Component
public class ControllerUtils {
    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(final MessageSource source) {
        messageSource = source;
    }

    private ControllerUtils() {
        // ControllerUtils is stateless
    }

    /**
     * Errors map:
     * key - field name
     * value - error message
     *
     * @param bindingResult - binding errors
     * @return errors map
     */
    public static Map<String, String> getErrors(final BindingResult bindingResult) {
        // TODO implement multi map, cos' fields can have multiple errors
        final Map<String, String> errors = new HashMap<>();
        // TODO Crutch ??
        for (final FieldError error : bindingResult.getFieldErrors()) {
            String errorMessage = null;
            for (final String errorCode : error.getCodes()) {
                errorMessage = messageSource.getMessage(errorCode, null, null, null);
                if (errorMessage != null) {
                    break;
                }
            }
            errors.put(error.getField(), (errorMessage != null) ? errorMessage : error.getDefaultMessage());
        }
        return errors;
    }
}
