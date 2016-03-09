package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.PaymentMethodEnum;
import com.tsystems.javaschool.milkroad.model.PaymentStatusEnum;
import com.tsystems.javaschool.milkroad.model.ShippingMethodEnum;
import com.tsystems.javaschool.milkroad.model.ShippingStatusEnum;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 15.02.2016.
 */
public class OrderDTO {
    private Long id;
    private UserDTO customer;
    private AddressDTO address;
    private BigDecimal totalPrice;
    private PaymentMethodEnum paymentMethod;
    private PaymentStatusEnum paymentStatus;
    private ShippingMethodEnum shippingMethod;
    private ShippingStatusEnum shippingStatus;
    private Date date;
    private String note;
    private List<Detail> details;

    public OrderDTO() {
        details = new ArrayList<>();
    }

    public OrderDTO(final Long id, final UserDTO customer, final AddressDTO address, final BigDecimal totalPrice,
                    final PaymentMethodEnum paymentMethod, final PaymentStatusEnum paymentStatus,
                    final ShippingMethodEnum shippingMethod, final ShippingStatusEnum shippingStatus,
                    final Date date, final String note) {
        this();
        this.id = id;
        this.customer = customer;
        this.address = address;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.shippingMethod = shippingMethod;
        this.shippingStatus = shippingStatus;
        this.date = date;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
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

    public void addDetail(final ProductDTO product, final int count, final BigDecimal totalPrice) {
        this.details.add(new Detail(product, count, totalPrice));
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;
        final OrderDTO orderDTO = (OrderDTO) o;
        return id != null ? id.equals(orderDTO.id) : orderDTO.id == null;
    }
}
