package com.algaworks.algashop.ordering.core.domain.model.customer;

import com.algaworks.algashop.ordering.core.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_CUSTOMER_ID_IS_INVALID;

public record CustomerId(UUID value) {

    public CustomerId {
        Objects.requireNonNull(value, VALIDATION_ERROR_CUSTOMER_ID_IS_INVALID);
    }

    public CustomerId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
