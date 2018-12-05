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
 * Criteria class for the Country entity. This class is used in CountryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /countries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CountryCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter countryId;

    private StringFilter countryCode;

    private StringFilter country3Code;

    private StringFilter phoneCode;

    private StringFilter numericCode;

    public CountryCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCountryId() {
        return countryId;
    }

    public void setCountryId(LongFilter countryId) {
        this.countryId = countryId;
    }

    public StringFilter getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(StringFilter countryCode) {
        this.countryCode = countryCode;
    }

    public StringFilter getCountry3Code() {
        return country3Code;
    }

    public void setCountry3Code(StringFilter country3Code) {
        this.country3Code = country3Code;
    }

    public StringFilter getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(StringFilter phoneCode) {
        this.phoneCode = phoneCode;
    }

    public StringFilter getNumericCode() {
        return numericCode;
    }

    public void setNumericCode(StringFilter numericCode) {
        this.numericCode = numericCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CountryCriteria that = (CountryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(countryId, that.countryId) &&
            Objects.equals(countryCode, that.countryCode) &&
            Objects.equals(country3Code, that.country3Code) &&
            Objects.equals(phoneCode, that.phoneCode) &&
            Objects.equals(numericCode, that.numericCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        countryId,
        countryCode,
        country3Code,
        phoneCode,
        numericCode
        );
    }

    @Override
    public String toString() {
        return "CountryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (countryId != null ? "countryId=" + countryId + ", " : "") +
                (countryCode != null ? "countryCode=" + countryCode + ", " : "") +
                (country3Code != null ? "country3Code=" + country3Code + ", " : "") +
                (phoneCode != null ? "phoneCode=" + phoneCode + ", " : "") +
                (numericCode != null ? "numericCode=" + numericCode + ", " : "") +
            "}";
    }

}
