package com.algaworks.algashop.ordering.domain.valueobject.id;

import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import io.hypersistence.tsid.TSID;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_ORDER_ITEM_ID_IS_INVALID;

public record OrderItemId(TSID value) {

    public OrderItemId {
        Objects.requireNonNull(value, VALIDATION_ERROR_ORDER_ITEM_ID_IS_INVALID);
    }

    public OrderItemId() {
        this(IdGenerator.generatiTSID());
    }

    public OrderItemId(Long value) {
        this(TSID.from(value));
    }

    public OrderItemId(String value) {
        this(TSID.from(value));
    }

    @Override
    public String toString() {
        return value().toString();
    }
}
