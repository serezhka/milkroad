package com.tsystems.javaschool.milkroad.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "product", schema = "milkroad")
@NamedQueries({
        @NamedQuery(name = "ProductEntity.findAllByCategory",
                query = "SELECT o FROM ProductEntity o WHERE o.category.categoryName = :category"),
        /* TODO Replace with native query MATCH AGAINST IN BOOLEAN MODE */
        @NamedQuery(name = "ProductEntity.findByPattern",
                query = "SELECT o FROM ProductEntity o WHERE LOWER(o.productName) LIKE :pattern")
})
public class ProductEntity {
    private Long id;
    private UserEntity seller;
    private ProductCategoryEntity category;
    private String productName;
    private BigDecimal productPrice;
    private Integer remainCount;
    private String description;

    // TODO It's ok ?
    private List<ProductParameterEntity> parameters = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    public UserEntity getSeller() {
        return seller;
    }

    public void setSeller(final UserEntity seller) {
        this.seller = seller;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    public ProductCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(final ProductCategoryEntity category) {
        this.category = category;
    }

    @Basic
    @Column(name = "product_name", nullable = false, length = 45)
    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "product_price", nullable = false, precision = 2)
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(final BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    @Basic
    @Column(name = "remain_count", nullable = false)
    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(final Integer remainCount) {
        this.remainCount = remainCount;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    public List<ProductParameterEntity> getParameters() {
        return parameters;
    }

    public void setParameters(final List<ProductParameterEntity> parameters) {
        this.parameters = parameters;
    }

    public ProductEntity() {
    }

    public ProductEntity(final Long id, final UserEntity seller, final ProductCategoryEntity category,
                         final String productName, final BigDecimal productPrice,
                         final Integer remainCount, final String description) {
        this.id = id;
        this.seller = seller;
        this.category = category;
        this.productName = productName;
        this.productPrice = productPrice;
        this.remainCount = remainCount;
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ProductEntity that = (ProductEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (productPrice != null ? !productPrice.equals(that.productPrice) : that.productPrice != null) return false;
        if (remainCount != null ? !remainCount.equals(that.remainCount) : that.remainCount != null) return false;
        //noinspection RedundantIfStatement
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productPrice != null ? productPrice.hashCode() : 0);
        result = 31 * result + (remainCount != null ? remainCount.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
