package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;
import static org.assertj.core.api.Assertions.*;

class CustomerTest {


    @Test
    void givenInvalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Customer.brandNew(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 25)),
                new Email("invalid-email"),
                new Phone("1234567890"),
                new Document("123.456.789-00"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("French Quarter")
                        .city("New Orleans")
                        .state("LA")
                        .zipCode(new ZipCode("70178"))
                        .build()
        )).withMessage(VALIDATION_ERROR_EMAIL_IS_INVALID);


    }

    @Test
    void givenInvalidEmail_whenTryUpdateCustomerEmail_shouldGenerateException() {
        Customer customer = Customer.brandNew(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 25)),
                new Email("joh.doe@gmail.com"),
                new Phone("1234567890"),
                new Document("123.456.789-00"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("French Quarter")
                        .city("New Orleans")
                        .state("LA")
                        .zipCode(new ZipCode("70178"))
                        .build()
        );

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                        customer.changeEmail(new Email("invalid-email")))
                .withMessage(VALIDATION_ERROR_EMAIL_IS_INVALID);


    }

    @Test
    void givenUnarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = Customer.brandNew(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 25)),
                new Email("joh.doe@gmail.com"),
                new Phone("1234567890"),
                new Document("123.456.789-00"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("French Quarter")
                        .city("New Orleans")
                        .state("LA")
                        .zipCode(new ZipCode("70178"))
                        .build()
        );

        customer.archive();

        assertWith(customer,
                c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous", "Anonymous")),
                c -> assertThat(c.email()).isNotEqualTo(new Email("john.doe@gmail.com")),
                c -> assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
                c -> assertThat(c.document()).isEqualTo(new Document("000-000-0000")),
                c -> assertThat(c.isArchived()).isTrue(),
                c -> assertThat(c.birthDate()).isNull(),
                c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
                c -> assertThat(c.address()).isEqualTo(
                        Address.builder()
                            .street("Bourbon Street")
                            .number("Anonymous")
                            .complement(null)
                            .neighborhood("French Quarter")
                            .city("New Orleans")
                            .state("LA")
                            .zipCode(new ZipCode("70178"))
                            .build())
        );
    }

    @Test
    void givenUArchivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = Customer.existing(
                new CustomerId(),
                new FullName("Anonymous", "Anonymous"),
                new BirthDate(LocalDate.of(1991, 7, 25)),
                new Email("joh.doe@gmail.com"),
                new Phone("1234567890"),
                new Document("123.456.789-00"),
                true,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                new LoyaltyPoints(10),
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("French Quarter")
                        .city("New Orleans")
                        .state("LA")
                        .zipCode(new ZipCode("70178"))
                        .build()
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
        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(() -> customer.changeEmail(new Email("jane.doe@gmail.com")))
                .withMessage(ERROR_CUSTOMER_ARCHIVED);
        assertThatExceptionOfType(CustomerArchivedException.class).isThrownBy(() -> customer.changePhone(new Phone("123-123-1234")))
                .withMessage(ERROR_CUSTOMER_ARCHIVED);

    }

    @Test
    void givenBrandNewCustomer_whenAddedPoints_shouldSumPoints() {
        Customer customer = Customer.brandNew(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 25)),
                new Email("joh.doe@gmail.com"),
                new Phone("1234567890"),
                new Document("123.456.789-00"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("French Quarter")
                        .city("New Orleans")
                        .state("LA")
                        .zipCode(new ZipCode("70178"))
                        .build()
        );

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(10));
    }

    @Test
    void givenBrandNewCustomer_whenAddedInvalidPoints_shouldGenerateException() {
        Customer customer = Customer.brandNew(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 25)),
                new Email("joh.doe@gmail.com"),
                new Phone("1234567890"),
                new Document("123.456.789-00"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("French Quarter")
                        .city("New Orleans")
                        .state("LA")
                        .zipCode(new ZipCode("70178"))
                        .build()
        );

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                () -> customer.addLoyaltyPoints(new LoyaltyPoints(-10)))
                .withMessage(VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(
                        () -> customer.addLoyaltyPoints(new LoyaltyPoints(0)))
                .withMessage(VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE);
    }

}