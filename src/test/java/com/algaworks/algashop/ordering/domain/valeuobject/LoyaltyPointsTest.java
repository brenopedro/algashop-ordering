package com.algaworks.algashop.ordering.domain.valeuobject;

import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.excpetion.ErrorMessages.VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class LoyaltyPointsTest {

    @Test
    void givenLoyaltyPoints_whenTryCreateLoyaltyPoints_shouldCreate() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        assertThat(loyaltyPoints.value()).isEqualTo(10);
    }

    @Test
    void givenLoyaltyPoints_whenTryAddLoyaltyPoints_shouldBeAdded() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        assertThat(loyaltyPoints.add(10).value()).isEqualTo(20);
    }

    @Test
    void givenLoyaltyPoints_whenTryAddNegativeLoyaltyPoints_shouldGenerateException() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> loyaltyPoints.add(-10))
                .withMessage(VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE);
    }


}