package com.tsystems.javaschool.milkroad.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "country", schema = "milkroad")
public class CountryEntity {
    private Long id;
    private String countryName;

    private List<CityEntity> cities;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "country_name", nullable = false, length = 45)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    public List<CityEntity> getCities() {
        return cities;
    }

    public void setCities(final List<CityEntity> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final CountryEntity that = (CountryEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        //noinspection RedundantIfStatement
        if (countryName != null ? !countryName.equals(that.countryName) : that.countryName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (countryName != null ? countryName.hashCode() : 0);
        return result;
    }
}
