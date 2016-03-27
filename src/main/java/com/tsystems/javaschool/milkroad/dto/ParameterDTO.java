package com.tsystems.javaschool.milkroad.dto;

/**
 * Created by Sergey on 20.03.2016.
 */
public class ParameterDTO {
    private AttributeDTO attribute;
    private String value;

    public ParameterDTO() {
    }

    public ParameterDTO(final AttributeDTO attribute, final String value) {
        this.attribute = attribute;
        this.value = value;
    }

    public AttributeDTO getAttribute() {
        return attribute;
    }

    public void setAttribute(final AttributeDTO attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
