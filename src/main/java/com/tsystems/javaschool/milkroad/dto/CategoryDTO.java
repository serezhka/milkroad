package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.ProductCategoryEntity;

/**
 * Created by Sergey on 24.02.2016.
 */
public class CategoryDTO {
    private String name;
    private String description;

    public CategoryDTO(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public  CategoryDTO(final ProductCategoryEntity categoryEntity) {
        this.name = categoryEntity.getCategoryName();
        this.description = categoryEntity.getDescription();
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
