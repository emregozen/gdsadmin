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
 * Criteria class for the CityName entity. This class is used in CityNameResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /city-names?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CityNameCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter cityNameId;

    private StringFilter cityCode;

    private StringFilter cityName;

    private StringFilter language;

    public CityNameCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCityNameId() {
        return cityNameId;
    }

    public void setCityNameId(LongFilter cityNameId) {
        this.cityNameId = cityNameId;
    }

    public StringFilter getCityCode() {
        return cityCode;
    }

    public void setCityCode(StringFilter cityCode) {
        this.cityCode = cityCode;
    }

    public StringFilter getCityName() {
        return cityName;
    }

    public void setCityName(StringFilter cityName) {
        this.cityName = cityName;
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
        final CityNameCriteria that = (CityNameCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(cityNameId, that.cityNameId) &&
            Objects.equals(cityCode, that.cityCode) &&
            Objects.equals(cityName, that.cityName) &&
            Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        cityNameId,
        cityCode,
        cityName,
        language
        );
    }

    @Override
    public String toString() {
        return "CityNameCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (cityNameId != null ? "cityNameId=" + cityNameId + ", " : "") +
                (cityCode != null ? "cityCode=" + cityCode + ", " : "") +
                (cityName != null ? "cityName=" + cityName + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
            "}";
    }

}
