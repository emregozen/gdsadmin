package com.gds.admin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Airline.
 */
@Entity
@Table(name = "airline")
@Document(indexName = "airline")
public class Airline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id")
    private Long id;

    @Transient
    private Long airlineId;

    @Column(name = "airline_code")
    private String airlineCode;

    @Column(name = "airline_name")
    private String airlineName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAirlineId() {
        return airlineId;
    }

    public Airline airlineId(Long airlineId) {
        this.airlineId = airlineId;
        return this;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public Airline airlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
        return this;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public Airline airlineName(String airlineName) {
        this.airlineName = airlineName;
        return this;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
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
        Airline airline = (Airline) o;
        if (airline.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), airline.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Airline{" +
            "id=" + getId() +
            ", airlineId=" + getAirlineId() +
            ", airlineCode='" + getAirlineCode() + "'" +
            ", airlineName='" + getAirlineName() + "'" +
            "}";
    }
}
