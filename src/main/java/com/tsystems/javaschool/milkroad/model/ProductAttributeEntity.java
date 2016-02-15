package com.tsystems.javaschool.milkroad.model;

import javax.persistence.*;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "product_attribute", schema = "milkroad")
public class ProductAttributeEntity {
    private Long id;
    private String attributeName;
    private String description;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "attribute_name", nullable = false, length = 45)
    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(final String attributeName) {
        this.attributeName = attributeName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ProductAttributeEntity that = (ProductAttributeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (attributeName != null ? !attributeName.equals(that.attributeName) : that.attributeName != null)
            return false;
        //noinspection RedundantIfStatement
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (attributeName != null ? attributeName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
