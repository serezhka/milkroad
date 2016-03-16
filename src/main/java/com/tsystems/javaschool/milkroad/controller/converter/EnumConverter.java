package com.tsystems.javaschool.milkroad.controller.converter;

import java.beans.PropertyEditorSupport;

/**
 * Created by Sergey on 16.03.2016.
 */
public class EnumConverter<T extends Enum<T>> extends PropertyEditorSupport {

    private final Class<T> clazz;

    public EnumConverter(final Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        final String upper = text.toUpperCase();
        final T value = T.valueOf(clazz, upper);
        setValue(value);
    }
}
