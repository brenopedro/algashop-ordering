package com.algaworks.algashop.ordering.domain.valueobject.id;

import io.hypersistence.tsid.TSID;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_ORDER_ID_IS_INVALID;

public record OrderId(TSID value) {

    public OrderId {
        Objects.requireNonNull(value, VALIDATION_ERROR_ORDER_ID_IS_INVALID);
    }

    public OrderId(Long value) {
        this(TSID.from(value));
    }

    public OrderId(String value) {
        this(TSID.from(value));
    }

    @Override
    public String toString() {
        return value().toString();
    }
}
