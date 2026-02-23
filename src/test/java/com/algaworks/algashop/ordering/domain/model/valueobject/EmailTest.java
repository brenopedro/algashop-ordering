package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


class EmailTest {

    @Test
    void givenInvalidEmail_whenTryCreateEmail_shouldGenerateException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Email("invalid-email"))
                .withMessage(VALIDATION_ERROR_EMAIL_IS_INVALID);
    }

}