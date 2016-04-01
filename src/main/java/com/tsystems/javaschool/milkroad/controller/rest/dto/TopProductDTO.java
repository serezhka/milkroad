package com.tsystems.javaschool.milkroad.controller.rest.dto;

import com.tsystems.javaschool.milkroad.dto.ProductDTO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Sergey on 30.03.2016.
 */
public class TopProductDTO implements Serializable {
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer remainCount;
    private Integer salesCount;

    public TopProductDTO() {
    }

    public TopProductDTO(final ProductDTO productDTO, final Integer salesCount) {
        name = productDTO.getName();
        description = productDTO.getDescription();
        category = productDTO.getCategory().getName();
        price = productDTO.getPrice();
        remainCount = productDTO.getCount();
        this.salesCount = salesCount;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(final Integer remainCount) {
        this.remainCount = remainCount;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(final Integer salesCount) {
        this.salesCount = salesCount;
    }
}
