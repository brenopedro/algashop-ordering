package com.algaworks.algashop.ordering.domain.model.commons;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.VALIDATION_ERROR_QUANTITY_IS_INVALID;

public record Quantity(Integer value) implements Comparable<Quantity> {

    public static final Quantity ZERO = new Quantity(0);

    public Quantity {
        Objects.requireNonNull(value, VALIDATION_ERROR_QUANTITY_IS_INVALID);
        if (value < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_IS_INVALID);
        }
    }

    public Quantity add(Quantity quantity) {
        Objects.requireNonNull(quantity, VALIDATION_ERROR_QUANTITY_IS_INVALID);
        return new Quantity(value() + quantity.value());
    }

    @Override
    public int compareTo(Quantity o) {
        return value().compareTo(o.value());
    }

    @Override
    public String toString() {
        return value().toString();
    }
}
