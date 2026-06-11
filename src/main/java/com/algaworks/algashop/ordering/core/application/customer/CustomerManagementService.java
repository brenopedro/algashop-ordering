package com.algaworks.algashop.ordering.core.application.customer;

import com.algaworks.algashop.ordering.core.ports.in.commons.AddressData;
import com.algaworks.algashop.ordering.core.domain.model.commons.*;
import com.algaworks.algashop.ordering.core.domain.model.customer.*;
import com.algaworks.algashop.ordering.core.ports.in.customer.CustomerInput;
import com.algaworks.algashop.ordering.core.ports.in.customer.CustomerUpdateInput;
import com.algaworks.algashop.ordering.core.ports.in.customer.ForManagingCustomers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerManagementService implements ForManagingCustomers {

    private final CustomerRegistrationService customerRegistrationService;
    private final Customers customers;

    @Transactional
    public UUID create(CustomerInput input) {
        Objects.requireNonNull(input);
        AddressData address = input.getAddress();

        Customer customer = customerRegistrationService.register(
                new FullName(input.getFirstName(), input.getLastName()),
                new BirthDate(input.getBirthDate()),
                new Email(input.getEmail()),
                new Phone(input.getPhone()),
                new Document(input.getDocument()),
                input.getPromotionNotificationsAllowed(),
                Address.builder()
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .neighborhood(address.getNeighborhood())
                        .city(address.getCity())
                        .state(address.getState())
                        .zipCode(new ZipCode(address.getZipCode()))
                        .build()
        );

        customers.add(customer);
        return customer.id().value();
    }

    @Transactional
    public void update(UUID rawCustomerId, CustomerUpdateInput input) {
        Objects.requireNonNull(rawCustomerId);
        Objects.requireNonNull(input);

        Customer customer = customers.ofId(new CustomerId(rawCustomerId))
                .orElseThrow(CustomerNotFoundException::new);

        customer.changeName(new FullName(input.getFirstName(), input.getLastName()));
        customer.changePhone(new Phone(input.getPhone()));

        if (Boolean.TRUE.equals(input.getPromotionNotificationsAllowed()))
            customer.enablePromotionNotifications();
        else
            customer.disablePromotionNotifications();

        AddressData address = input.getAddress();

        customer.changeAddress(Address.builder()
                .zipCode(new ZipCode(address.getZipCode()))
                .state(address.getState())
                .city(address.getCity())
                .neighborhood(address.getNeighborhood())
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .build());

        customers.add(customer);
    }

    @Transactional
    public void archive(UUID rawCustomerId) {
        Objects.requireNonNull(rawCustomerId);

        Customer customer = customers.ofId(new CustomerId(rawCustomerId)).orElseThrow(CustomerNotFoundException::new);

        customer.archive();
        customers.add(customer);
    }

    @Transactional
    public void changeEmail(UUID rawCustomerId, String email) {
        Objects.requireNonNull(email);

        Customer customer = customers.ofId(new CustomerId(rawCustomerId)).orElseThrow(CustomerNotFoundException::new);

        customerRegistrationService.changeEmail(customer, new Email(email));
        customers.add(customer);
    }
}
