package com.gds.admin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AirportName.
 */
@Entity
@Table(name = "airport_name")
@Document(indexName = "airportname")
public class AirportName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_name_id")
    private Long id;

    @Transient
    private Long airportNameId;

    @Column(name = "airport_code")
    private String airportCode;

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAirportNameId() {
        return airportNameId;
    }

    public AirportName airportNameId(Long airportNameId) {
        this.airportNameId = airportNameId;
        return this;
    }

    public void setAirportNameId(Long airportNameId) {
        this.airportNameId = airportNameId;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public AirportName airportCode(String airportCode) {
        this.airportCode = airportCode;
        return this;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getLanguage() {
        return language;
    }

    public AirportName language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
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
        AirportName airportName = (AirportName) o;
        if (airportName.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), airportName.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AirportName{" +
            "id=" + getId() +
            ", airportNameId=" + getAirportNameId() +
            ", airportCode='" + getAirportCode() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
