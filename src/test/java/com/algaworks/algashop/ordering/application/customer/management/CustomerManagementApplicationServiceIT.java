package com.algaworks.algashop.ordering.application.customer.management;


import com.algaworks.algashop.ordering.domain.model.customer.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerEmailIsInUseException;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService customerManagementApplicationService;

    @Test
    void shouldRegister() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();

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
    void shouldUpdate() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();

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

    @Test
    void shouldArchiveCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        customerManagementApplicationService.archive(customerId);

        CustomerOutput archivedCustomer = customerManagementApplicationService.findById(customerId);

        assertThat(archivedCustomer)
                .isNotNull()
                .extracting(
                        CustomerOutput::getFirstName,
                        CustomerOutput::getLastName,
                        CustomerOutput::getPhone,
                        CustomerOutput::getDocument,
                        CustomerOutput::getBirthDate,
                        CustomerOutput::getPromotionNotificationsAllowed
                ).containsExactly(
                        "Anonymous",
                        "Anonymous",
                        "000-000-0000",
                        "000-00-0000",
                        null,
                        false
                );

        assertThat(archivedCustomer.getEmail()).endsWith("@anonymous.com");
        assertThat(archivedCustomer.getArchived()).isTrue();
        assertThat(archivedCustomer.getArchivedAt()).isNotNull();

        assertThat(archivedCustomer.getAddress()).isNotNull();
        assertThat(archivedCustomer.getAddress().getNumber()).isNotNull().isEqualTo("Anonymized");
        assertThat(archivedCustomer.getAddress().getComplement()).isNull();
    }

    @Test
    void shouldThrowCustomerNotFoundExceptionWhenArchivingNonExistingCustomer() {
        UUID nonExistingId = UUID.randomUUID();

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerManagementApplicationService.archive(nonExistingId));
    }

    @Test
    void shouldThrowCustomerArchivedExceptionWhenArchivingAlreadyArchivedCustomer() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        customerManagementApplicationService.archive(customerId);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customerManagementApplicationService.archive(customerId));
    }

    @Test
    void givenValidCustomerAndEmail_whenChangeEmail_shouldChangeEmail() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        customerManagementApplicationService.changeEmail(customerId, "johndoe2@email.com");

        CustomerOutput customerSaved = customerManagementApplicationService.findById(customerId);

        assertThat(customerSaved.getEmail()).endsWith("johndoe2@email.com");
    }

    @Test
    void givenInexistentCustomerId_whenChangeEmail_shouldThrowCustomerNotFoundException() {
        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(UUID.randomUUID(), "johndoe2@email.com"));
    }

    @Test
    void givenArchivedCustomer_whenChangeEmail_shouldThrowCustomerArchivedException() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        customerManagementApplicationService.archive(customerId);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId, "johndoe2@email.com"));

    }

    @Test
    void givenInvalidEmailFormat_whenChangeEmail_shouldThrowIllegalArgumentException() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId, "johnemail"));
    }

    @Test
    void givenEmailInUse_whenChangeEmail_shouldThrowCustomerEmailIsInUseException() {
        CustomerInput input = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId = customerManagementApplicationService.create(input);
        assertThat(customerId).isNotNull();

        customerManagementApplicationService.changeEmail(customerId, "johndoe2@email.com");

        CustomerInput input2 = CustomerInputTestDataBuilder.aCustomer().build();
        UUID customerId2 = customerManagementApplicationService.create(input2);
        assertThat(customerId2).isNotNull();

        assertThatExceptionOfType(CustomerEmailIsInUseException.class)
                .isThrownBy(() -> customerManagementApplicationService.changeEmail(customerId2, "johndoe2@email.com"));
    }
}