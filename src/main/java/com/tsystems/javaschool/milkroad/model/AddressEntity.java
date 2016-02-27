package com.tsystems.javaschool.milkroad.model;

import com.tsystems.javaschool.milkroad.dto.AddressDTO;

import javax.persistence.*;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "address", schema = "milkroad")
@NamedQueries({
        @NamedQuery(name = "AddressEntity.findAddressesByUserEmail", query = "SELECT o FROM AddressEntity o WHERE o.user.email = :email")
})
public class AddressEntity {
    private Long id;
    private UserEntity user;
    //    private CountryEntity country;
//    private CityEntity city;
    private String country;
    private String city;
    private Integer postcode;
    private String street;
    private String building;
    private String apartment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(final UserEntity user) {
        this.user = user;
    }

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(final CountryEntity country) {
        this.country = country;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    public CityEntity getCity() {
        return city;
    }

    public void setCity(final CityEntity city) {
        this.city = city;
    }*/

    @Basic
    @Column(name = "country_id", nullable = false, length = 45)
    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "city_id", nullable = false, length = 45)
    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "postcode", nullable = false)
    public Integer getPostcode() {
        return postcode;
    }

    public void setPostcode(final Integer postcode) {
        this.postcode = postcode;
    }

    @Basic
    @Column(name = "street", nullable = false, length = 45)
    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "building", nullable = false, length = 45)
    public String getBuilding() {
        return building;
    }

    public void setBuilding(final String building) {
        this.building = building;
    }

    @Basic
    @Column(name = "apartment", nullable = false, length = 45)
    public String getApartment() {
        return apartment;
    }

    public void setApartment(final String apartment) {
        this.apartment = apartment;
    }

    public AddressEntity() {
    }

    public AddressEntity(final AddressDTO addressDTO) {
        this.country = addressDTO.getCountry();
        this.city = addressDTO.getCity();
        this.postcode = addressDTO.getPostcode();
        this.street = addressDTO.getStreet();
        this.building = addressDTO.getBuilding();
        this.apartment = addressDTO.getApartment();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final AddressEntity that = (AddressEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (postcode != null ? !postcode.equals(that.postcode) : that.postcode != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (building != null ? !building.equals(that.building) : that.building != null) return false;
        //noinspection RedundantIfStatement
        if (apartment != null ? !apartment.equals(that.apartment) : that.apartment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (building != null ? building.hashCode() : 0);
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        return result;
    }
}
