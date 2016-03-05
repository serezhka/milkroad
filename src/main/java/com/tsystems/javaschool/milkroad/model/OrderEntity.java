package com.tsystems.javaschool.milkroad.model;

import com.tsystems.javaschool.milkroad.dto.OrderDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "`order`", schema = "milkroad")
@NamedQueries({
        @NamedQuery(name = "OrderEntity.getTopCustomers",
                query = "SELECT o.customer, sum(o.priceTotal) FROM OrderEntity o " +
                        "GROUP BY o.customer ORDER BY sum(o.priceTotal) DESC"),
        @NamedQuery(name = "OrderEntity.getTotalCash",
                query = "SELECT sum(o.priceTotal) FROM OrderEntity o"),
        @NamedQuery(name = "OrderEntity.getTotalCashByPeriod",
                query = "SELECT sum(o.priceTotal) FROM OrderEntity o " +
                        "WHERE o.order_date >= :from AND o.order_date <= :to")
})
public class OrderEntity {
    private Long id;
    private UserEntity customer;
    private AddressEntity address;
    private BigDecimal priceTotal;
    private PaymentMethodEnum paymentMethod;
    private ShippingMethodEnum shippingMethod;
    private PaymentStatusEnum paymentStatus;
    private ShippingStatusEnum shippingStatus;
    private Date order_date;
    private String note;

    // TODO It's ok ?
    private List<OrderDetailEntity> orderDetails = new ArrayList<>();

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
    @JoinColumn(name = "customer_id")
    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(final UserEntity customer) {
        this.customer = customer;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(final AddressEntity address) {
        this.address = address;
    }

    @Basic
    @Column(name = "price_total", nullable = false, precision = 2)
    public BigDecimal getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(final BigDecimal priceTotal) {
        this.priceTotal = priceTotal;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_method", nullable = false)
    public ShippingMethodEnum getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(final ShippingMethodEnum shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    public PaymentStatusEnum getPaymentStatus() {
        return (paymentStatus == null) ? PaymentStatusEnum.AWAITING : paymentStatus;
    }

    public void setPaymentStatus(final PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_status", nullable = false)
    public ShippingStatusEnum getShippingStatus() {
        return (shippingStatus == null) ? ShippingStatusEnum.AWAITING : shippingStatus;
    }

    public void setShippingStatus(final ShippingStatusEnum shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    @Basic
    @Column(name = "order_date", nullable = false)
    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(final Date order_date) {
        this.order_date = order_date;
    }

    @Basic
    @Column(name = "note", nullable = true, length = -1)
    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    public List<OrderDetailEntity> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(final List<OrderDetailEntity> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addOrderDetail(final OrderDetailEntity orderDetailEntity) {
        this.orderDetails.add(orderDetailEntity);
        orderDetailEntity.setOrder(this);
    }

    public OrderEntity() {
    }

    public OrderEntity(final OrderDTO orderDTO) {
        this.paymentMethod = orderDTO.getPaymentMethod();
        this.shippingMethod = orderDTO.getShippingMethod();
        this.priceTotal = orderDTO.getTotalPrice();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final OrderEntity that = (OrderEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (customer != null ? !customer.equals(that.customer) : that.customer != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (priceTotal != null ? !priceTotal.equals(that.priceTotal) : that.priceTotal != null) return false;
        if (paymentMethod != that.paymentMethod) return false;
        if (shippingMethod != that.shippingMethod) return false;
        if (paymentStatus != that.paymentStatus) return false;
        if (shippingStatus != that.shippingStatus) return false;
        //noinspection RedundantIfStatement
        if (note != null ? !note.equals(that.note) : that.note != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (priceTotal != null ? priceTotal.hashCode() : 0);
        result = 31 * result + (paymentMethod != null ? paymentMethod.hashCode() : 0);
        result = 31 * result + (shippingMethod != null ? shippingMethod.hashCode() : 0);
        result = 31 * result + (paymentStatus != null ? paymentStatus.hashCode() : 0);
        result = 31 * result + (shippingStatus != null ? shippingStatus.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }
}
