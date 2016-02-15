package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.MrAddressEntity;
import com.tsystems.javaschool.milkroad.model.MrUserEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 10.02.2016.
 */
public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String email;
    private List<AddressDTO> addresses;

    public UserDTO() {
        addresses = new ArrayList<>();
    }

    public UserDTO(final long id, final String firstName, final String lastName, final Date birthday,
                   final String email) {
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
    }

    public UserDTO(final MrUserEntity userEntity) {
        this();
        this.id = userEntity.getId();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.birthday = userEntity.getBirthday();
        this.email = userEntity.getEmail();
        for (final MrAddressEntity mrAddressEntity : userEntity.getAdresses()) {
            addresses.add(new AddressDTO(mrAddressEntity));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
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

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(final List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}
