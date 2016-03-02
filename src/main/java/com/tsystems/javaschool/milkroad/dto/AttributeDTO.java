package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.ProductAttributeEntity;

/**
 * Created by Sergey on 01.03.2016.
 */
public class AttributeDTO {
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

    public AttributeDTO(final ProductAttributeEntity attributeEntity) {
        this.id = attributeEntity.getId();
        this.name = attributeEntity.getAttributeName();
        this.description = attributeEntity.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
