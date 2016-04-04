package com.tsystems.javaschool.milkroad.controller.form;

import java.io.Serializable;

/**
 * Created by Sergey on 04.04.2016.
 */
public class FilterAttribute implements Serializable {
    private Long id;
    private String minValue;
    private String maxValue;

    public FilterAttribute() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(final String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(final String maxValue) {
        this.maxValue = maxValue;
    }
}
