package com.tsystems.javaschool.milkroad.dto;

import com.tsystems.javaschool.milkroad.model.AddressEntity;

/**
 * Created by Sergey on 14.02.2016.
 */
public class AddressDTO {
    private Long id;
    private String country;
    private String city;
    private Integer postcode;
    private String street;
    private String building;
    private String apartment;

    public AddressDTO() {
    }

    public AddressDTO(final Long id, final String country, final String city, final Integer postcode, final String street, final String building, final String apartment) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.postcode = postcode;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

    public AddressDTO(final AddressEntity addressEntity) {
        this.id = addressEntity.getId();
        this.country = addressEntity.getCountry().getCountryName();
        this.city = addressEntity.getCity().getCityName();
        this.postcode = addressEntity.getPostcode();
        this.street = addressEntity.getStreet();
        this.building = addressEntity.getBuilding();
        this.apartment = addressEntity.getApartment();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

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

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

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
}
