package com.tsystems.javaschool.milkroad.dto;

import java.io.Serializable;

/**
 * Created by Sergey on 20.03.2016.
 */
public class ParameterDTO implements Serializable {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ParameterDTO)) return false;
        final ParameterDTO that = (ParameterDTO) o;
        //noinspection SimplifiableIfStatement
        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = attribute != null ? attribute.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
