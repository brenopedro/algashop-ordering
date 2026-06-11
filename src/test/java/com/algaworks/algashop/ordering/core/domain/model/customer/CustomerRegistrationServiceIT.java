package com.algaworks.algashop.ordering.core.domain.model.customer;

import com.algaworks.algashop.ordering.core.domain.model.commons.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerRegistrationServiceIT {

    @Autowired
    private CustomerRegistrationService service;

    @Test
    void shouldRegister() {
        Customer customer = service.register(
                new FullName("John","Doe"),
                new BirthDate(LocalDate.of(1991, 7,5)),
                new Email("johndoe@email.com"),
                new Phone("478-256-2604"),
                new Document("255-08-0578"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("York")
                        .state("South California")
                        .zipCode(new ZipCode("12345"))
                        .complement("Apt. 114").build()
        );

        assertThat(customer.fullName()).isEqualTo(new FullName("John","Doe"));
        assertThat(customer.birthDate()).isEqualTo(new BirthDate(LocalDate.of(1991, 7,5)));
    }
}