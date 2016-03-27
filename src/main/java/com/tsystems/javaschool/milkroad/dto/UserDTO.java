package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.UserTypeEnum;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 10.02.2016.
 */
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String email;
    private UserTypeEnum userType;
    private List<AddressDTO> addresses;

    public UserDTO() {
        addresses = new ArrayList<>();
    }

    public UserDTO(final Long id, final String firstName, final String lastName, final Date birthday,
                   final String email, final UserTypeEnum userType) {
        this();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Size(min = 1, max = 45, message = "Name must not be blank, not exceed 45 characters")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Size(min = 1, max = 45, message = "Last name must not be blank, not exceed 45 characters")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    @Size(min = 1, max = 130, message = "Invalid email")
    @Email(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "Invalid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(final UserTypeEnum userType) {
        this.userType = userType;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(final List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(final AddressDTO addressDTO) {
        this.addresses.add(addressDTO);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        final UserDTO userDTO = (UserDTO) o;
        return id != null ? id.equals(userDTO.id) : userDTO.id == null;
    }
}
