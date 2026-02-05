package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.excpetion.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.valeuobject.CustomerId;
import com.algaworks.algashop.ordering.domain.valeuobject.FullName;
import com.algaworks.algashop.ordering.domain.valeuobject.LoyaltyPoints;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static com.algaworks.algashop.ordering.domain.excpetion.ErrorMessages.*;
import static org.assertj.core.api.Assertions.*;

class CustomerTest {


    @Test
    void givenInvalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
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
                new CustomerId(),
                new FullName("John", "Doe"),
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

    @Test
    void givenUnarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 25),
                "joh.doe@gmail.com",
                "1234567890",
                "123.456.789-00",
                true,
                OffsetDateTime.now()
        );

        customer.archive();

        assertWith(customer,
                c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
                c -> assertThat(c.email()).isNotEqualTo("john.doe@gmail.com"),
                c -> assertThat(c.phone()).isEqualTo("000-000-0000"),
                c -> assertThat(c.document()).isEqualTo("000-000-0000"),
                c -> assertThat(c.isArchived()).isTrue(),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse()
        );
    }

    @Test
    void givenUArchivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous", "Anonymous"),
                LocalDate.of(1991, 7, 25),
                "joh.doe@gmail.com",
                "1234567890",
                "123.456.789-00",
                true,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                new LoyaltyPoints(10)
        );

        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(customer::archive)
                .withMessage(ERROR_CUSTOMER_ARCHIVED);
        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(10)))
                .withMessage(ERROR_CUSTOMER_ARCHIVED);
        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(customer::enablePromotionNotifications)
                .withMessage(ERROR_CUSTOMER_ARCHIVED);
        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(customer::disablePromotionNotifications)
                .withMessage(ERROR_CUSTOMER_ARCHIVED);
        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(() -> customer.changeName(new FullName("Jane", "Doe")))
                .withMessage(ERROR_CUSTOMER_ARCHIVED);
        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(() -> customer.changeEmail("jane.doe@gmail.com"))
                .withMessage(ERROR_CUSTOMER_ARCHIVED);
        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(() -> customer.changePhone("123-123-1234"))
                .withMessage(ERROR_CUSTOMER_ARCHIVED);

    }

    @Test
    void givenBrandNewCustomer_whenAddedPoints_shouldSumPoints() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 25),
                "joh.doe@gmail.com",
                "1234567890",
                "123.456.789-00",
                true,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(10));
    }

    @Test
    void givenBrandNewCustomer_whenAddedInvalidPoints_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                LocalDate.of(1991, 7, 25),
                "joh.doe@gmail.com",
                "1234567890",
                "123.456.789-00",
                true,
                OffsetDateTime.now()
        );

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> customer.addLoyaltyPoints(new LoyaltyPoints(-10)))
                .withMessage(VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                        () -> customer.addLoyaltyPoints(new LoyaltyPoints(0)))
                .withMessage(VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE);
    }

}