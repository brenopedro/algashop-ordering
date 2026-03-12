package com.algaworks.algashop.ordering.application.service;


import com.algaworks.algashop.ordering.application.model.AddressData;
import com.algaworks.algashop.ordering.application.model.CustomerInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService service;

    @Test
    void shouldRegister() {
        CustomerInput input = CustomerInput.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1991, 7,5))
                .document("255-08-0578")
                .phone("478-256-2604")
                .email("johndoe@email.com")
                .promotionNotificationsAllowed(false)
                .address(AddressData.builder()
                        .street("Bourbon Street")
                        .number("1200")
                        .complement("Apt. 901")
                        .neighborhood("North Ville")
                        .city("Yostfort")
                        .state("South Carolina")
                        .zipCode("70283")
                        .build())
                .build();

        UUID customerId = service.create(input);
        assertThat(customerId).isNotNull();
    }
}