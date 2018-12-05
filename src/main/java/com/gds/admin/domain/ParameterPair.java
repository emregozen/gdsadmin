package com.gds.admin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ParameterPair.
 */
@Entity
@Table(name = "parameter_pair")
@Document(indexName = "parameterpair")
public class ParameterPair implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parameter_pair_id")
    private Long id;

    @Transient
    private Long parameterPairId;

    @Column(name = "parameter_key")
    private String parameterKey;

    @Column(name = "parameter_value")
    private String parameterValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParameterPairId() {
        return parameterPairId;
    }

    public ParameterPair parameterPairId(Long parameterPairId) {
        this.parameterPairId = parameterPairId;
        return this;
    }

    public void setParameterPairId(Long parameterPairId) {
        this.parameterPairId = parameterPairId;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public ParameterPair parameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
        return this;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public ParameterPair parameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
        return this;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
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
        ParameterPair parameterPair = (ParameterPair) o;
        if (parameterPair.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parameterPair.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ParameterPair{" +
            "id=" + getId() +
            ", parameterPairId=" + getParameterPairId() +
            ", parameterKey='" + getParameterKey() + "'" +
            ", parameterValue='" + getParameterValue() + "'" +
            "}";
    }
}
