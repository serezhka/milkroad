package com.tsystems.javaschool.milkroad.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Sergey on 01.03.2016.
 */
public class AttributeDTO implements Serializable {
    private Long id;
    private String name;
    private String description;

    public AttributeDTO() {
    }

    public AttributeDTO(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Size(min = 1, max = 45, message = "Name must not be blank, not exceed 45 characters")
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Size(max = 45, message = "Description must not exceed 45 characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeDTO)) return false;
        final AttributeDTO that = (AttributeDTO) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
