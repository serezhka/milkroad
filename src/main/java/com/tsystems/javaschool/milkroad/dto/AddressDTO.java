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

    public AddressDTO(final String country, final String city, final Integer postcode, final String street, final String building, final String apartment) {
        this(null, country, city, postcode, street, building, apartment);
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
//        this.country = addressEntity.getCountry().getCountryName();
        this.country = addressEntity.getCountry();
//        this.city = addressEntity.getCity().getCityName();
        this.city = addressEntity.getCity();
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressDTO)) return false;

        final AddressDTO that = (AddressDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (postcode != null ? !postcode.equals(that.postcode) : that.postcode != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        //noinspection SimplifiableIfStatement
        if (building != null ? !building.equals(that.building) : that.building != null) return false;
        return apartment != null ? apartment.equals(that.apartment) : that.apartment == null;
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
}
