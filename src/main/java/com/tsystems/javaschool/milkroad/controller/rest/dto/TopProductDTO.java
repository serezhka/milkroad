package com.tsystems.javaschool.milkroad.controller.rest.dto;

import com.tsystems.javaschool.milkroad.dto.ProductDTO;

/**
 * Created by Sergey on 30.03.2016.
 */
public class TopProductDTO {
    private ProductDTO info;
    private Integer sales;

    public TopProductDTO() {
    }

    public TopProductDTO(final ProductDTO info, final Integer sales) {
        this.info = info;
        this.sales = sales;
    }

    public ProductDTO getInfo() {
        return info;
    }

    public void setInfo(final ProductDTO info) {
        this.info = info;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(final Integer sales) {
        this.sales = sales;
    }
}
