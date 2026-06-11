package com.algaworks.algashop.ordering.core.application.customer.loyaltypoints;

import com.algaworks.algashop.ordering.core.application.AbstractApplicationIT;
import com.algaworks.algashop.ordering.core.application.customer.CustomerLoyaltyPointsService;
import com.algaworks.algashop.ordering.core.domain.model.commons.Email;
import com.algaworks.algashop.ordering.core.domain.model.commons.Money;
import com.algaworks.algashop.ordering.core.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.core.domain.model.customer.*;
import com.algaworks.algashop.ordering.core.domain.model.order.*;
import com.algaworks.algashop.ordering.core.domain.model.product.Product;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.listener.customer.CustomerEventListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CustomerLoyaltyPointsApplicationServiceIT extends AbstractApplicationIT {

    @Autowired
    CustomerLoyaltyPointsService loyaltyPointsService;

    @Autowired
    Customers customers;

    @Autowired
    Orders orders;

    @MockitoBean
    CustomerEventListener customerEventListener;


    @Test
    void givenValidCustomer_whenAddedLoyaltyPoints_shouldAddToCustomer() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        Order order = OrderTestDataBuilder.anOrder()
                .customerId(customer.id())
                .status(OrderStatus.READY)
                .withItems(true)
                .build();
        orders.add(order);

        loyaltyPointsService.addLoyaltyPoints(customer.id().value(), order.id().toString());

        Customer updatedCustomer = customers.ofId(customer.id()).orElseThrow();
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void givenInvalidCustomerId_whenAddedLoyaltyPoints_shouldThrowCustomerNotFoundException() {
        UUID nonExistingCustomerId = UUID.randomUUID();

        Customer dummyCustomer = CustomerTestDataBuilder.brandNewCustomer()
                .email(new Email("dummy@example.com")).build();
        customers.add(dummyCustomer);

        Order order = OrderTestDataBuilder.anOrder()
                .customerId(dummyCustomer.id())
                .status(OrderStatus.READY)
                .build();
        orders.add(order);

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> loyaltyPointsService.addLoyaltyPoints(nonExistingCustomerId, order.id().toString()));
    }

    @Test
    void givenInvalidOrderId_whenAddedLoyaltyPoints_shouldThrowOrderNotFoundException() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer()
                .email(new Email("dummy@example.com")).build();
        customers.add(customer);

        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> loyaltyPointsService.addLoyaltyPoints(customer.id().value(), new OrderId().toString()));
    }

    @Test
    void givenArchivedCustomer_whenAddedLoyaltyPoints_shouldThrowCustomerArchivedException() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer()
                .email(new Email("dummy@example.com")).build();
        customers.add(customer);
        customer.archive();
        customers.add(customer);

        Order order = OrderTestDataBuilder.anOrder()
                .customerId(customer.id())
                .status(OrderStatus.READY)
                .withItems(true)
                .build();
        orders.add(order);

        assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> loyaltyPointsService.addLoyaltyPoints(customer.id().value(), order.id().toString()));
    }

    @Test
    void givenOrderOfAnotherCustomer_whenAddedLoyaltyPoints_shouldThrowOrderDoesNotBelongToCustomerException() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer()
                .email(new Email("customer@example.com")).build();
        customers.add(customer);

        Customer customer2 = CustomerTestDataBuilder.brandNewCustomer()
                .email(new Email("customer2@example.com")).build();
        customers.add(customer2);

        Order order = OrderTestDataBuilder.anOrder()
                .customerId(customer2.id())
                .status(OrderStatus.READY)
                .withItems(true)
                .build();
        orders.add(order);

        assertThatExceptionOfType(OrderDoesNotBelongToCustomerException.class)
                .isThrownBy(() -> loyaltyPointsService.addLoyaltyPoints(customer.id().value(), order.id().toString()));
    }

    @Test
    void givenOrderNotInReady_whenAddedLoyaltyPoints_shouldThrowCantAddLoyaltyPointsOrderIsNotReadyException() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer()
                .email(new Email("customer@example.com")).build();
        customers.add(customer);

        Order order = OrderTestDataBuilder.anOrder()
                .customerId(customer.id())
                .status(OrderStatus.DRAFT)
                .withItems(true)
                .build();
        orders.add(order);

        assertThatExceptionOfType(CantAddLoyaltyPointsOrderIsNotReadyException.class)
                .isThrownBy(() -> loyaltyPointsService.addLoyaltyPoints(customer.id().value(), order.id().toString()));
    }

    @Test
    void givenOrderWithLowPrice_whenAddedLoyaltyPoints_shouldNotAddToCustomer() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        Order order = OrderTestDataBuilder.anOrder()
                .customerId(customer.id())
                .status(OrderStatus.DRAFT)
                .withItems(false)
                .build();
        Product product = ProductTestDataBuilder.aProduct().price(new Money("100")).build();

        order.addItem(product, new Quantity(1));
        order.place();
        order.markAsPaid();
        order.markAsReady();

        orders.add(order);

        loyaltyPointsService.addLoyaltyPoints(customer.id().value(), order.id().toString());

        Customer updatedCustomer = customers.ofId(customer.id()).orElseThrow();
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.loyaltyPoints()).isEqualTo(LoyaltyPoints.ZERO);
    }
}