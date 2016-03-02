package com.tsystems.javaschool.milkroad.model;

import com.tsystems.javaschool.milkroad.dto.CategoryDTO;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "product_category", schema = "milkroad")
@NamedQueries({
        @NamedQuery(name = "ProductCategoryEntity.findByName",
                query = "SELECT o FROM ProductCategoryEntity o WHERE o.categoryName = :name")
})
public class ProductCategoryEntity {
    private Long id;
    private String categoryName;
    private String description;

    private List<ProductEntity> products;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "category_name", nullable = false, length = 45)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(final List<ProductEntity> products) {
        this.products = products;
    }

    public ProductCategoryEntity(){
    }

    public ProductCategoryEntity(final CategoryDTO categoryDTO) {
        this.categoryName = categoryDTO.getName();
        this.description = categoryDTO.getDescription();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ProductCategoryEntity that = (ProductCategoryEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;
        //noinspection RedundantIfStatement
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
