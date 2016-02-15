package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
public class OrderDTO {
    private long id;
    private UserDTO customer;
    private AddressDTO address;
    private BigDecimal totalPrice;
    private PaymentMethodEnum paymentMethod;
    private PaymentStatusEnum paymentStatus;
    private ShippingMethodEnum shippingMethod;
    private ShippingStatusEnum shippingStatus;
    private String note;
    private List<Detail> details;

    public OrderDTO() {
        details = new ArrayList<>();
    }

    public OrderDTO(final long id, final UserDTO customer, final AddressDTO address, final BigDecimal totalPrice,
                    final PaymentMethodEnum paymentMethod, final PaymentStatusEnum paymentStatus,
                    final ShippingMethodEnum shippingMethod, final ShippingStatusEnum shippingStatus,
                    final String note, final List<Detail> details) {
        this();
        this.id = id;
        this.customer = customer;
        this.address = address;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.shippingMethod = shippingMethod;
        this.shippingStatus = shippingStatus;
        this.note = note;
        this.details = details;
    }

    public OrderDTO(final UserDTO customer, final AddressDTO address, final MrOrderEntity orderEntity) {
        this();
        this.id = orderEntity.getId();
        this.customer = customer;
        this.address = address;
        this.totalPrice = orderEntity.getPriceTotal();
        this.paymentMethod = orderEntity.getPaymentMethod();
        this.paymentStatus = orderEntity.getPaymentStatus();
        this.shippingMethod = orderEntity.getShippingMethod();
        this.shippingStatus = orderEntity.getShippingStatus();
        this.note = orderEntity.getNote();
        for (final MrOrderDetailEntity orderDetailEntity : orderEntity.getOrderDetails()) {
            details.add(new Detail(orderDetailEntity));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public UserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(final UserDTO customer) {
        this.customer = customer;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(final AddressDTO address) {
        this.address = address;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public ShippingMethodEnum getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(final ShippingMethodEnum shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public ShippingStatusEnum getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(final ShippingStatusEnum shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(final List<Detail> details) {
        this.details = details;
    }

    public class Detail {
        private ProductDTO product;
        private int count;
        private BigDecimal totalPrice;

        public Detail() {
        }

        public Detail(final ProductDTO product, final int count, final BigDecimal totalPrice) {
            this.product = product;
            this.count = count;
            this.totalPrice = totalPrice;
        }

        public Detail(final MrOrderDetailEntity orderDetailEntity) {
            this.product = new ProductDTO(orderDetailEntity.getProduct());
            this.count = orderDetailEntity.getProductCount();
            this.totalPrice = orderDetailEntity.getPriceTotal();
        }

        public ProductDTO getProduct() {
            return product;
        }

        public void setProduct(final ProductDTO product) {
            this.product = product;
        }

        public int getCount() {
            return count;
        }

        public void setCount(final int count) {
            this.count = count;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(final BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }
    }
}
