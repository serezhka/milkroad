package com.tsystems.javaschool.milkroad.model;

import javax.persistence.*;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "mr_product_parameter", schema = "milkroad")
public class MrProductParameterEntity {
    private Long id; // TODO Stub. Fix me. PK must be (product, attribute)
    private MrProductEntity product;
    private MrProductAttributeEntity attribute;
    private String attributeValue;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    public MrProductEntity getProduct() {
        return product;
    }

    public void setProduct(final MrProductEntity product) {
        this.product = product;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attribute_id")
    public MrProductAttributeEntity getAttribute() {
        return attribute;
    }

    public void setAttribute(final MrProductAttributeEntity attribute) {
        this.attribute = attribute;
    }

    @Basic
    @Column(name = "attribute_value", nullable = true, length = 45)
    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(final String attributeValue) {
        this.attributeValue = attributeValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof MrProductParameterEntity)) return false;

        final MrProductParameterEntity that = (MrProductParameterEntity) o;

        if (!id.equals(that.id)) return false;
        if (!product.equals(that.product)) return false;
        //noinspection SimplifiableIfStatement
        if (!attribute.equals(that.attribute)) return false;
        return attributeValue.equals(that.attributeValue);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + product.hashCode();
        result = 31 * result + attribute.hashCode();
        result = 31 * result + attributeValue.hashCode();
        return result;
    }
}
