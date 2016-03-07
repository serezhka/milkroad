package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.ProductCategoryEntity;

/**
 * Created by Sergey on 24.02.2016.
 */
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;

    public CategoryDTO() {
    }

    public CategoryDTO(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public CategoryDTO(final ProductCategoryEntity categoryEntity) {
        this.id = categoryEntity.getId();
        this.name = categoryEntity.getCategoryName();
        this.description = categoryEntity.getDescription();
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryDTO)) return false;

        final CategoryDTO that = (CategoryDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        //noinspection SimplifiableIfStatement
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
