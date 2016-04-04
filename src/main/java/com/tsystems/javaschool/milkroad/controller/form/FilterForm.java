package com.tsystems.javaschool.milkroad.controller.form;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 27.03.2016.
 */
public class FilterForm implements Serializable {
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<FilterAttribute> attributes;

    public FilterForm() {
        attributes = new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    @Min(value = 0, message = "Min price must be positive numeric value")
    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(final BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    @Min(value = 0, message = "Max price must be positive numeric value")
    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(final BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<FilterAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(final List<FilterAttribute> attributes) {
        this.attributes = attributes;
    }
}
