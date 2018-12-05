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
 * Criteria class for the Airport entity. This class is used in AirportResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /airports?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AirportCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter airportId;

    private StringFilter airportCode;

    private StringFilter cityCode;

    private StringFilter countryCode;

    private BooleanFilter isDomestic;

    public AirportCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAirportId() {
        return airportId;
    }

    public void setAirportId(LongFilter airportId) {
        this.airportId = airportId;
    }

    public StringFilter getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(StringFilter airportCode) {
        this.airportCode = airportCode;
    }

    public StringFilter getCityCode() {
        return cityCode;
    }

    public void setCityCode(StringFilter cityCode) {
        this.cityCode = cityCode;
    }

    public StringFilter getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(StringFilter countryCode) {
        this.countryCode = countryCode;
    }

    public BooleanFilter getIsDomestic() {
        return isDomestic;
    }

    public void setIsDomestic(BooleanFilter isDomestic) {
        this.isDomestic = isDomestic;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AirportCriteria that = (AirportCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(airportId, that.airportId) &&
            Objects.equals(airportCode, that.airportCode) &&
            Objects.equals(cityCode, that.cityCode) &&
            Objects.equals(countryCode, that.countryCode) &&
            Objects.equals(isDomestic, that.isDomestic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        airportId,
        airportCode,
        cityCode,
        countryCode,
        isDomestic
        );
    }

    @Override
    public String toString() {
        return "AirportCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (airportId != null ? "airportId=" + airportId + ", " : "") +
                (airportCode != null ? "airportCode=" + airportCode + ", " : "") +
                (cityCode != null ? "cityCode=" + cityCode + ", " : "") +
                (countryCode != null ? "countryCode=" + countryCode + ", " : "") +
                (isDomestic != null ? "isDomestic=" + isDomestic + ", " : "") +
            "}";
    }

}
