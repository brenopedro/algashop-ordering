package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_CUSTOMER_ID_IS_NULL;

public record CustomerId(UUID value) {

    public CustomerId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    public CustomerId {
        Objects.requireNonNull(value, VALIDATION_ERROR_CUSTOMER_ID_IS_NULL);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
