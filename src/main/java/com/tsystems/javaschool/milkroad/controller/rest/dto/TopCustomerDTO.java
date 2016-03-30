package com.tsystems.javaschool.milkroad.controller.rest.dto;

import com.tsystems.javaschool.milkroad.dto.UserDTO;

import java.math.BigDecimal;

/**
 * Created by Sergey on 30.03.2016.
 */
public class TopCustomerDTO {
    private UserDTO info;
    private BigDecimal cash;

    public TopCustomerDTO() {
    }

    public TopCustomerDTO(final UserDTO info, final BigDecimal cash) {
        this.info = info;
        this.cash = cash;
    }

    public UserDTO getInfo() {
        return info;
    }

    public void setUser(final UserDTO info) {
        this.info = info;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(final BigDecimal cash) {
        this.cash = cash;
    }
}
