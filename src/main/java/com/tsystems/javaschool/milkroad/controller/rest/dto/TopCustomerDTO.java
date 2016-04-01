package com.tsystems.javaschool.milkroad.controller.rest.dto;

import com.tsystems.javaschool.milkroad.dto.UserDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Sergey on 30.03.2016.
 */
public class TopCustomerDTO implements Serializable {
    private String firstName;
    private String lastName;
    private Date birthday;
    private String email;
    private BigDecimal cash;

    public TopCustomerDTO() {
    }

    public TopCustomerDTO(final UserDTO userDTO, final BigDecimal cash) {
        firstName = userDTO.getFirstName();
        lastName = userDTO.getLastName();
        birthday = userDTO.getBirthday();
        email = userDTO.getEmail();
        this.cash = cash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(final BigDecimal cash) {
        this.cash = cash;
    }
}
