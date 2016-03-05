package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.AddressEntity;
import com.tsystems.javaschool.milkroad.model.UserEntity;
import com.tsystems.javaschool.milkroad.model.UserTypeEnum;

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

    // TODO It's ok ?
    private List<AddressDTO> addresses = new ArrayList<>();

    public UserDTO() {
    }

    public UserDTO(final String firstName, final String lastName, final Date birthday,
                   final String email, final UserTypeEnum type) {
        this(null, firstName, lastName, birthday, email, type);
    }

    public UserDTO(final Long id, final String firstName, final String lastName, final Date birthday,
                   final String email, final UserTypeEnum userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.userType = userType;
    }

    public UserDTO(final UserEntity userEntity) {
        this.id = userEntity.getId();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.birthday = userEntity.getBirthday();
        this.email = userEntity.getEmail();
        this.userType = userEntity.getUserType();
        for (final AddressEntity addressEntity : userEntity.getAdresses()) {
            addresses.add(new AddressDTO(addressEntity));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        final UserDTO userDTO = (UserDTO) o;

        if (id != null ? !id.equals(userDTO.id) : userDTO.id != null) return false;
        if (firstName != null ? !firstName.equals(userDTO.firstName) : userDTO.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDTO.lastName) : userDTO.lastName != null) return false;
        if (birthday != null ? !birthday.equals(userDTO.birthday) : userDTO.birthday != null) return false;
        if (email != null ? !email.equals(userDTO.email) : userDTO.email != null) return false;
        //noinspection SimplifiableIfStatement
        if (userType != userDTO.userType) return false;
        return addresses != null ? addresses.equals(userDTO.addresses) : userDTO.addresses == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthday.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + userType.hashCode();
        return result;
    }
}
