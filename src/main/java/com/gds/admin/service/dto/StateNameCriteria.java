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
 * Criteria class for the StateName entity. This class is used in StateNameResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /state-names?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StateNameCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter stateNameId;

    private StringFilter countryCode;

    private StringFilter stateCode;

    private StringFilter stateName;

    private StringFilter language;

    public StateNameCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getStateNameId() {
        return stateNameId;
    }

    public void setStateNameId(LongFilter stateNameId) {
        this.stateNameId = stateNameId;
    }

    public StringFilter getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(StringFilter countryCode) {
        this.countryCode = countryCode;
    }

    public StringFilter getStateCode() {
        return stateCode;
    }

    public void setStateCode(StringFilter stateCode) {
        this.stateCode = stateCode;
    }

    public StringFilter getStateName() {
        return stateName;
    }

    public void setStateName(StringFilter stateName) {
        this.stateName = stateName;
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
        final StateNameCriteria that = (StateNameCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(stateNameId, that.stateNameId) &&
            Objects.equals(countryCode, that.countryCode) &&
            Objects.equals(stateCode, that.stateCode) &&
            Objects.equals(stateName, that.stateName) &&
            Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        stateNameId,
        countryCode,
        stateCode,
        stateName,
        language
        );
    }

    @Override
    public String toString() {
        return "StateNameCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stateNameId != null ? "stateNameId=" + stateNameId + ", " : "") +
                (countryCode != null ? "countryCode=" + countryCode + ", " : "") +
                (stateCode != null ? "stateCode=" + stateCode + ", " : "") +
                (stateName != null ? "stateName=" + stateName + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
            "}";
    }

}
