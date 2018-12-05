package com.gds.admin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Airport.
 */
@Entity
@Table(name = "airport")
@Document(indexName = "airport")
public class Airport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private Long id;

    @Transient
    private Long airportId;

    @Column(name = "airport_code")
    private String airportCode;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "is_domestic")
    private Boolean isDomestic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAirportId() {
        return airportId;
    }

    public Airport airportId(Long airportId) {
        this.airportId = airportId;
        return this;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public Airport airportCode(String airportCode) {
        this.airportCode = airportCode;
        return this;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public Airport cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Airport countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Boolean isIsDomestic() {
        return isDomestic;
    }

    public Airport isDomestic(Boolean isDomestic) {
        this.isDomestic = isDomestic;
        return this;
    }

    public void setIsDomestic(Boolean isDomestic) {
        this.isDomestic = isDomestic;
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
        Airport airport = (Airport) o;
        if (airport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), airport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Airport{" +
            "id=" + getId() +
            ", airportId=" + getAirportId() +
            ", airportCode='" + getAirportCode() + "'" +
            ", cityCode='" + getCityCode() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", isDomestic='" + isIsDomestic() + "'" +
            "}";
    }
}
