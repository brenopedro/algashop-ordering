package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRegistrationServiceTest {

    @InjectMocks
    private CustomerRegistrationService service;

    @Mock
    private Customers customers;

    @Test
    void shouldRegister() {
        when(customers.isEmailUnique(any(Email.class), any(CustomerId.class)))
                .thenReturn(true);

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