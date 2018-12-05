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
 * Criteria class for the Message entity. This class is used in MessageResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /messages?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessageCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter messageId;

    private StringFilter messageKey;

    private StringFilter messageValue;

    private StringFilter language;

    public MessageCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getMessageId() {
        return messageId;
    }

    public void setMessageId(LongFilter messageId) {
        this.messageId = messageId;
    }

    public StringFilter getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(StringFilter messageKey) {
        this.messageKey = messageKey;
    }

    public StringFilter getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(StringFilter messageValue) {
        this.messageValue = messageValue;
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
        final MessageCriteria that = (MessageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(messageId, that.messageId) &&
            Objects.equals(messageKey, that.messageKey) &&
            Objects.equals(messageValue, that.messageValue) &&
            Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        messageId,
        messageKey,
        messageValue,
        language
        );
    }

    @Override
    public String toString() {
        return "MessageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (messageId != null ? "messageId=" + messageId + ", " : "") +
                (messageKey != null ? "messageKey=" + messageKey + ", " : "") +
                (messageValue != null ? "messageValue=" + messageValue + ", " : "") +
                (language != null ? "language=" + language + ", " : "") +
            "}";
    }

}
