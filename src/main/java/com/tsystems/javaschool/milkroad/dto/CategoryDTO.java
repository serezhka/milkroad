package com.tsystems.javaschool.milkroad.dto;

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
        return id != null ? id.equals(that.id) : that.id == null;
    }
}
