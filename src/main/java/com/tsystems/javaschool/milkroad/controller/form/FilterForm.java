package com.tsystems.javaschool.milkroad.controller.form;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Sergey on 27.03.2016.
 */
public class FilterForm {
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<Long> attributes;

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(final BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(final BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<Long> getAttributes() {
        return attributes;
    }

    public void setAttributes(final List<Long> attributes) {
        this.attributes = attributes;
    }
}
