package com.tsystems.javaschool.milkroad.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Sergey on 14.02.2016.
 */
public class AddressDTO implements Serializable {
    private Long id;
    private String country;
    private String city;
    private Integer postcode;
    private String street;
    private String building;
    private String apartment;

    public AddressDTO() {
    }

    public AddressDTO(final Long id, final String country, final String city, final Integer postcode,
                      final String street, final String building, final String apartment) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Size(min = 1, max = 45, message = "Country must not be blank, not exceed 45 characters")
    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    @Size(min = 1, max = 45, message = "City must not be blank, not exceed 45 characters")
    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(final Integer postcode) {
        this.postcode = postcode;
    }

    @Size(min = 1, max = 45, message = "Street must not be blank, not exceed 45 characters")
    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    @Size(min = 1, max = 45, message = "Building must not be blank, not exceed 45 characters")
    public String getBuilding() {
        return building;
    }

    public void setBuilding(final String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(final String apartment) {
        this.apartment = apartment;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDTO)) return false;
        final AddressDTO that = (AddressDTO) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (building != null ? building.hashCode() : 0);
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return postcode + ", " + country + ", " + city + ", " + street + ", " + building + ", " + apartment;
    }
}
