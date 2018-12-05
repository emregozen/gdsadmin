package com.gds.admin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CountryName.
 */
@Entity
@Table(name = "country_name")
@Document(indexName = "countryname")
public class CountryName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_name_id")
    private Long id;

    @Transient
    private Long countryNameId;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCountryNameId() {
        return countryNameId;
    }

    public CountryName countryNameId(Long countryNameId) {
        this.countryNameId = countryNameId;
        return this;
    }

    public void setCountryNameId(Long countryNameId) {
        this.countryNameId = countryNameId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public CountryName countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public CountryName countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLanguage() {
        return language;
    }

    public CountryName language(String language) {
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
        CountryName countryName = (CountryName) o;
        if (countryName.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), countryName.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CountryName{" +
            "id=" + getId() +
            ", countryNameId=" + getCountryNameId() +
            ", countryCode='" + getCountryCode() + "'" +
            ", countryName='" + getCountryName() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
