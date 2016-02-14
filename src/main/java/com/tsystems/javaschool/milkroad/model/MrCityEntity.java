package com.tsystems.javaschool.milkroad.model;

import javax.persistence.*;

/**
 * Created by Sergey on 13.02.2016.
 */
@Entity
@Table(name = "mr_city", schema = "milkroad")
public class MrCityEntity {
    private Long id;
    private MrCountryEntity country;
    private String cityName;

    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    public MrCountryEntity getCountry() {
        return country;
    }

    public void setCountry(final MrCountryEntity country) {
        this.country = country;
    }

    @Basic
    @Column(name = "city_name", nullable = false, length = 45)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MrCityEntity that = (MrCityEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        //noinspection RedundantIfStatement
        if (cityName != null ? !cityName.equals(that.cityName) : that.cityName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (cityName != null ? cityName.hashCode() : 0);
        return result;
    }
}
