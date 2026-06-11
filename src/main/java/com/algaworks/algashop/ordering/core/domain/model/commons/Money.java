package com.algaworks.algashop.ordering.core.domain.model.commons;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_MONEY_IS_INVALID;
import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_QUANTITY_IS_INVALID;

public record Money(BigDecimal value) implements Comparable<Money> {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_MONEY_IS_INVALID);
        this.value = value.setScale(2, RoundingMode.HALF_EVEN);
        if (value().signum() == -1) {
            throw new IllegalArgumentException(VALIDATION_ERROR_MONEY_IS_INVALID);
        }
    }

    public Money(String value) {
        this(new BigDecimal(value));
    }

    public Money multiply(Quantity quantity) {
        Objects.requireNonNull(quantity, VALIDATION_ERROR_QUANTITY_IS_INVALID);
        if (quantity.value() < 1) {
            throw new IllegalArgumentException(VALIDATION_ERROR_QUANTITY_IS_INVALID);
        }
        return new Money(value().multiply(new BigDecimal(quantity.value())));
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, VALIDATION_ERROR_MONEY_IS_INVALID);
        return new Money(value().add(other.value()));
    }

    public Money divide(Money other) {
        return new Money(value().divide(other.value(), RoundingMode.HALF_EVEN));
    }

    @Override
    public int compareTo(Money o) {
        return this.value().compareTo(o.value());
    }

    @Override
    public String toString() {
        return value().toString();
    }
}
