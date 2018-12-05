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
 * Criteria class for the CountryName entity. This class is used in CountryNameResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /country-names?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryNameCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter countryNameId;

    private StringFilter countryCode;

    private StringFilter countryName;

    private StringFilter language;

    public CountryNameCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCountryNameId() {
        return countryNameId;
    }

    public void setCountryNameId(LongFilter countryNameId) {
        this.countryNameId = countryNameId;
    }

    public StringFilter getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(StringFilter countryCode) {
        this.countryCode = countryCode;
    }

    public StringFilter getCountryName() {
        return countryName;
    }

    public void setCountryName(StringFilter countryName) {
        this.countryName = countryName;
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
        final CountryNameCriteria that = (CountryNameCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(countryNameId, that.countryNameId) &&
            Objects.equals(countryCode, that.countryCode) &&
            Objects.equals(countryName, that.countryName) &&
            Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        countryNameId,
        countryCode,
        countryName,
        language
        );
    }

    @Override
    public String toString() {
        return "CountryNameCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (countryNameId != null ? "countryNameId=" + countryNameId + ", " : "") +
                (countryCode != null ? "countryCode=" + countryCode + ", " : "") +
                (countryName != null ? "countryName=" + countryName + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
            "}";
    }

}
