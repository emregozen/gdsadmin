package com.gds.admin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CityName.
 */
@Entity
@Table(name = "city_name")
@Document(indexName = "cityname")
public class CityName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_name_id")
    private Long id;

    @Transient
    private Long cityNameId;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityNameId() {
        return cityNameId;
    }

    public CityName cityNameId(Long cityNameId) {
        this.cityNameId = cityNameId;
        return this;
    }

    public void setCityNameId(Long cityNameId) {
        this.cityNameId = cityNameId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public CityName cityCode(String cityCode) {
        this.cityCode = cityCode;
        return this;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public CityName cityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLanguage() {
        return language;
    }

    public CityName language(String language) {
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
        CityName cityName = (CityName) o;
        if (cityName.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cityName.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CityName{" +
            "id=" + getId() +
            ", cityNameId=" + getCityNameId() +
            ", cityCode='" + getCityCode() + "'" +
            ", cityName='" + getCityName() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
