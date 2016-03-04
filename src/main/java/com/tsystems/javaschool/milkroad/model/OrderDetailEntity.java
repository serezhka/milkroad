package com.tsystems.javaschool.milkroad.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "order_detail", schema = "milkroad")
@NamedQueries({
        @NamedQuery(name = "OrderDetailEntity.getTopProducts",
                query = "SELECT o.product, sum(o.productCount) FROM OrderDetailEntity o " +
                        "GROUP BY o.product ORDER BY sum(o.productCount) DESC")
})
public class OrderDetailEntity {
    private Long id; // TODO Stub. Fix me. PK must be (product, attribute)
    private OrderEntity order;
    private ProductEntity product;
    private Integer productCount;
    private BigDecimal priceTotal;

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
    @JoinColumn(name = "order_id")
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(final OrderEntity order) {
        this.order = order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(final ProductEntity productId) {
        this.product = productId;
    }

    @Basic
    @Column(name = "product_count", nullable = false)
    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(final Integer productCount) {
        this.productCount = productCount;
    }

    @Basic
    @Column(name = "price_total", nullable = false, precision = 2)
    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(final BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailEntity)) return false;

        final OrderDetailEntity that = (OrderDetailEntity) o;

        if (!id.equals(that.id)) return false;
        if (!order.equals(that.order)) return false;
        if (!product.equals(that.product)) return false;
        //noinspection SimplifiableIfStatement
        if (!productCount.equals(that.productCount)) return false;
        return priceTotal.equals(that.priceTotal);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + order.hashCode();
        result = 31 * result + product.hashCode();
        result = 31 * result + productCount.hashCode();
        result = 31 * result + priceTotal.hashCode();
        return result;
    }
}
