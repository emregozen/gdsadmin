package com.gds.admin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Document(indexName = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private Long id;

    @Transient
    private Long countryId;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_3_code")
    private String country3Code;

    @Column(name = "phone_code")
    private String phoneCode;

    @Column(name = "numeric_code")
    private String numericCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCountryId() {
        return countryId;
    }

    public Country countryId(Long countryId) {
        this.countryId = countryId;
        return this;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Country countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry3Code() {
        return country3Code;
    }

    public Country country3Code(String country3Code) {
        this.country3Code = country3Code;
        return this;
    }

    public void setCountry3Code(String country3Code) {
        this.country3Code = country3Code;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public Country phoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
        return this;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public Country numericCode(String numericCode) {
        this.numericCode = numericCode;
        return this;
    }

    public void setNumericCode(String numericCode) {
        this.numericCode = numericCode;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        if (country.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryId=" + getCountryId() +
            ", countryCode='" + getCountryCode() + "'" +
            ", country3Code='" + getCountry3Code() + "'" +
            ", phoneCode='" + getPhoneCode() + "'" +
            ", numericCode='" + getNumericCode() + "'" +
            "}";
    }
}
