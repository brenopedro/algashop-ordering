package com.algaworks.algashop.ordering.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class BirthDateTest {

    @Test
    void givenInvalidBirthDate_whenTryCreateBirthDate_shouldGenerateException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            new BirthDate(LocalDate.now().plusDays(1));
        }).withMessage(VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
    }
}