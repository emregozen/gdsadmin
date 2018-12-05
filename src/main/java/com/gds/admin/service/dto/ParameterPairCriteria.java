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
 * Criteria class for the ParameterPair entity. This class is used in ParameterPairResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /parameter-pairs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParameterPairCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter parameterPairId;

    private StringFilter parameterKey;

    private StringFilter parameterValue;

    public ParameterPairCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getParameterPairId() {
        return parameterPairId;
    }

    public void setParameterPairId(LongFilter parameterPairId) {
        this.parameterPairId = parameterPairId;
    }

    public StringFilter getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(StringFilter parameterKey) {
        this.parameterKey = parameterKey;
    }

    public StringFilter getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(StringFilter parameterValue) {
        this.parameterValue = parameterValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParameterPairCriteria that = (ParameterPairCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(parameterPairId, that.parameterPairId) &&
            Objects.equals(parameterKey, that.parameterKey) &&
            Objects.equals(parameterValue, that.parameterValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        parameterPairId,
        parameterKey,
        parameterValue
        );
    }

    @Override
    public String toString() {
        return "ParameterPairCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (parameterPairId != null ? "parameterPairId=" + parameterPairId + ", " : "") +
                (parameterKey != null ? "parameterKey=" + parameterKey + ", " : "") +
                (parameterValue != null ? "parameterValue=" + parameterValue + ", " : "") +
            "}";
    }

}
