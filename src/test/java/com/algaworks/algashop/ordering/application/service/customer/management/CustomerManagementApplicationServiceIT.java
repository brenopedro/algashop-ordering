package com.algaworks.algashop.ordering.application.service.customer.management;


import com.algaworks.algashop.ordering.application.customer.management.CustomerManagementApplicationService;
import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.customer.management.CustomerInput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerOutput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerUpdateInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService customerManagementApplicationService;

    @Test
    public void shouldRegister() {
        CustomerInput input = CustomerInputTestDataBuilder.builder().build();

        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);

        assertThat(customerOutput).extracting(
                CustomerOutput::getId,
                CustomerOutput::getFirstName,
                CustomerOutput::getLastName,
                CustomerOutput::getEmail,
                CustomerOutput::getBirthDate
        ).containsExactly(
                customerId,
                "John",
                "Doe",
                "johndoe@email.com",
                LocalDate.of(1991, 7,5)
        );

        assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }

    @Test
    public void shouldUpdate() {
        CustomerInput input = CustomerInputTestDataBuilder.builder().build();

        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        CustomerUpdateInput updateInput = CustomerUpdateInputTestDateBuilder.builder().build();
        customerManagementApplicationService.update(customerId, updateInput);

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);

        assertThat(customerOutput).extracting(
                CustomerOutput::getId,
                CustomerOutput::getFirstName,
                CustomerOutput::getLastName,
                CustomerOutput::getEmail,
                CustomerOutput::getBirthDate
        ).containsExactly(
                customerId,
                "Matt",
                "Damon",
                "johndoe@email.com",
                LocalDate.of(1991, 7,5)
        );

        assertThat(customerOutput.getRegisteredAt()).isNotNull();
    }
}