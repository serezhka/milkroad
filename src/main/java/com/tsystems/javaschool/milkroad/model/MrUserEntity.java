package com.tsystems.javaschool.milkroad.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by Sergey on 11.02.2016.
 */
@Entity
@Table(name = "mr_user", schema = "milkroad")
@NamedQueries({
        @NamedQuery(name = "MrUserEntity.findByEmail", query = "SELECT o FROM MrUserEntity o WHERE o.email = :email")
})
public class MrUserEntity {
    private Long id;
    private UserTypeEnum userType;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String email;
    private String passHash;
    private String passSalt;

    private List<MrAddressEntity> adresses;
    private List<MrProductEntity> products;
    private List<MrOrderEntity> orders;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(final UserTypeEnum userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "birthday", nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 130)
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "pass_hash", nullable = false, length = 32)
    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(final String passHash) {
        this.passHash = passHash;
    }

    @Basic
    @Column(name = "pass_salt", nullable = false, length = 32)
    public String getPassSalt() {
        return passSalt;
    }

    public void setPassSalt(final String passSalt) {
        this.passSalt = passSalt;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<MrAddressEntity> getAdresses() {
        return adresses;
    }

    public void setAdresses(final List<MrAddressEntity> adresses) {
        this.adresses = adresses;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
    public List<MrProductEntity> getProducts() {
        return products;
    }

    public void setProducts(final List<MrProductEntity> products) {
        this.products = products;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    public List<MrOrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(final List<MrOrderEntity> orders) {
        this.orders = orders;
    }

    public MrUserEntity() {
    }

    public MrUserEntity(final Long id, final UserTypeEnum userType, final String firstName,
                        final String lastName, final Date birthday, final String email,
                        final String passHash, final String passSalt) {
        this.id = id;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.email = email;
        this.passHash = passHash;
        this.passSalt = passSalt;
    }

    public MrUserEntity(final MrUserEntity userEntity) {
        this.id = userEntity.id;
        this.userType = userEntity.userType;
        this.firstName = userEntity.firstName;
        this.lastName = userEntity.lastName;
        this.birthday = userEntity.birthday;
        this.email = userEntity.email;
        this.passHash = userEntity.passHash;
        this.passSalt = userEntity.passSalt;

        this.adresses = userEntity.getAdresses();
        this.products = userEntity.getProducts();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final MrUserEntity that = (MrUserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userType != that.userType) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null)
            return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (passHash != null ? !passHash.equals(that.passHash) : that.passHash != null)
            return false;
        //noinspection RedundantIfStatement
        if (passSalt != null ? !passSalt.equals(that.passSalt) : that.passSalt != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (passHash != null ? passHash.hashCode() : 0);
        result = 31 * result + (passSalt != null ? passSalt.hashCode() : 0);
        return result;
    }
}
