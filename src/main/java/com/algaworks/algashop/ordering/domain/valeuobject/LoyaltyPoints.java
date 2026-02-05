package com.algaworks.algashop.ordering.domain.valeuobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.excpetion.ErrorMessages.VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE;

public record LoyaltyPoints(Integer value) implements Comparable<LoyaltyPoints> {

    public LoyaltyPoints() {
        this(0);
    }

    public LoyaltyPoints(Integer value) {
        Objects.requireNonNull(value);
        if (value < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE);
        }
        this.value = value;
    }

    public LoyaltyPoints add(Integer points) {
        return add(new LoyaltyPoints(points));
    }

    public LoyaltyPoints add(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        if (loyaltyPoints.value() < 0) {
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
