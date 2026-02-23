package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTestDataBuilder {

    private CustomerTestDataBuilder() {

    }

    public static Customer.BrandNewCustomerBuilder brandNewCustomer() {
        return Customer.brandNew()
                .fullName(new FullName("John", "Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 25)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("1234567890"))
                .document(new Document("123.456.789-00"))
                .promotionNotificationsAllowed(true)
                .address(Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("French Quarter")
                        .city("New Orleans")
                        .state("LA")
                        .zipCode(new ZipCode("70178"))
                        .build());
    }

    public static Customer.ExistingCustomerBuilder existingAnonymisedCustomer() {
        return Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("Anonymous", "Anonymous"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 25)))
                .email(new Email("anonymous@anonymous.com"))
                .phone(new Phone("000-000-0000"))
                .document(new Document("000-00-0000"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(new LoyaltyPoints(10))
                .address( Address.builder()
                        .street("Bourbon Street")
                        .number("Anonymous")
                        .complement(null)
                        .neighborhood("French Quarter")
                        .city("New Orleans")
                        .state("LA")
                        .zipCode(new ZipCode("70178"))
                        .build());
    }
}
