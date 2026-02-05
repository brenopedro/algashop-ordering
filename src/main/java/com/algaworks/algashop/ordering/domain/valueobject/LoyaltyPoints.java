package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_LOYALTY_POINTS_IS_NULL;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE;

public record LoyaltyPoints(Integer value) implements Comparable<LoyaltyPoints> {

    public static final LoyaltyPoints ZERO = new LoyaltyPoints(0);

    public LoyaltyPoints() {
        this(0);
    }

    public LoyaltyPoints {
        Objects.requireNonNull(value, VALIDATION_ERROR_LOYALTY_POINTS_IS_NULL);
        if (value < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE);
        }
    }

    public LoyaltyPoints add(Integer points) {
        return add(new LoyaltyPoints(points));
    }

    public LoyaltyPoints add(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints, VALIDATION_ERROR_LOYALTY_POINTS_IS_NULL);
        if (loyaltyPoints.value() <= 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE);
        }
        return new LoyaltyPoints(this.value() + loyaltyPoints.value());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(LoyaltyPoints other) {
        return this.value().compareTo(other.value);
    }
}
