package com.gds.admin.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the AirportName entity. This class is used in AirportNameResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /airport-names?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AirportNameCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter airportNameId;

    private StringFilter airportCode;

    private StringFilter language;

    public AirportNameCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAirportNameId() {
        return airportNameId;
    }

    public void setAirportNameId(LongFilter airportNameId) {
        this.airportNameId = airportNameId;
    }

    public StringFilter getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(StringFilter airportCode) {
        this.airportCode = airportCode;
    }

    public StringFilter getLanguage() {
        return language;
    }

    public void setLanguage(StringFilter language) {
        this.language = language;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AirportNameCriteria that = (AirportNameCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(airportNameId, that.airportNameId) &&
            Objects.equals(airportCode, that.airportCode) &&
            Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        airportNameId,
        airportCode,
        language
        );
    }

    @Override
    public String toString() {
        return "AirportNameCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (airportNameId != null ? "airportNameId=" + airportNameId + ", " : "") +
                (airportCode != null ? "airportCode=" + airportCode + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
            "}";
    }

}
