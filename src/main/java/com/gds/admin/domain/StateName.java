package com.gds.admin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StateName.
 */
@Entity
@Table(name = "state_name")
@Document(indexName = "statename")
public class StateName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_name_id")
    private Long id;

    @Transient
    private Long stateNameId;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "language")
    private String language;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStateNameId() {
        return stateNameId;
    }

    public StateName stateNameId(Long stateNameId) {
        this.stateNameId = stateNameId;
        return this;
    }

    public void setStateNameId(Long stateNameId) {
        this.stateNameId = stateNameId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public StateName countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public StateName stateCode(String stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public StateName stateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLanguage() {
        return language;
    }

    public StateName language(String language) {
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
        StateName stateName = (StateName) o;
        if (stateName.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stateName.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StateName{" +
            "id=" + getId() +
            ", stateNameId=" + getStateNameId() +
            ", countryCode='" + getCountryCode() + "'" +
            ", stateCode='" + getStateCode() + "'" +
            ", stateName='" + getStateName() + "'" +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
