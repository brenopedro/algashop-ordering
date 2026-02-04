package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static com.algaworks.algashop.ordering.domain.excpetion.ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CustomerTest {


    @Test
    void givenInvalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 25),
                "invalid-email",
                "1234567890",
                "123.456.789-00",
                true,
                OffsetDateTime.now()
        )).withMessage(VALIDATION_ERROR_EMAIL_IS_INVALID);


    }

    @Test
    void givenInvalidEmail_whenTryUpdateCustomerEmail_shouldGenerateException() {
        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 25),
                "joh.doe@gmail.com",
                "1234567890",
                "123.456.789-00",
                true,
                OffsetDateTime.now()
        );

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                        customer.changeEmail("invalid-email"))
                .withMessage(VALIDATION_ERROR_EMAIL_IS_INVALID);


    }

}