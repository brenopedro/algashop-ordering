package com.algaworks.algashop.ordering.application.order.management;

import com.algaworks.algashop.ordering.application.customer.loyaltypoints.CustomerLoyaltyPointsApplicationService;
import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.order.*;
import com.algaworks.algashop.ordering.infrastructure.listener.order.OrderEventListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class OrderManagementApplicationServiceIT {

    @Autowired
    private OrderManagementApplicationService service;

    @Autowired
    Orders orders;

    @Autowired
    Customers customers;

    @MockitoSpyBean
    OrderEventListener orderEventListener;

    @MockitoSpyBean
    private CustomerLoyaltyPointsApplicationService customerLoyaltyPointsApplicationService;

    Customer customer;
    Order order;

    @BeforeEach
    void setUp() {
        customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        order = OrderTestDataBuilder.anOrder()
                .customerId(customer.id())
                .withItems(true)
                .status(OrderStatus.DRAFT)
                .build();
        orders.add(order);
    }

    @Test
    void givenValidOrder_whenCancel_shouldCancel() {
        service.cancel(order.id().value().toLong());

        Order orderSaved = orders.ofId(order.id()).orElseThrow();

        assertThat(orderSaved.isCanceled()).isTrue();
        verify(orderEventListener).listen(any(OrderCanceledEvent.class));
    }

    @Test
    void givenInvalidOrder_whenCancel_shouldThrowOrderNotFoundException() {
        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> service.cancel(new OrderId().value().toLong()));
    }

    @Test
    void givenCanceledOrder_whenCancel_shouldThrowOrderStatusCannotBeChangedException() {
        service.cancel(order.id().value().toLong());

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(() -> service.cancel(order.id().value().toLong()));
    }

    @Test
    void givenValidOrder_whenMarkAsPaid_shouldChangeStatusToPaid() {
        order.place();
        orders.add(order);
        service.markAsPaid(order.id().value().toLong());

        Order orderSaved = orders.ofId(order.id()).orElseThrow();

        assertThat(orderSaved.isPaid()).isTrue();
        verify(orderEventListener).listen(any(OrderPaidEvent.class));
    }

    @Test
    void givenInvalidOrder_whenMarkAsPaid_shouldThrowOrderNotFoundException() {
        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> service.markAsPaid(new OrderId().value().toLong()));
    }

    @Test
    void givenCanceledOrder_whenMarkAsPaid_shouldThrowOrderStatusCannotBeChangedException() {
        order.cancel();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(() -> service.markAsPaid(order.id().value().toLong()));
    }

    @Test
    void givenValidOrder_whenMarkAsReady_shouldChangeStatusToPaid() {
        order.place();
        order.markAsPaid();
        orders.add(order);
        service.markAsReady(order.id().value().toLong());

        Order orderSaved = orders.ofId(order.id()).orElseThrow();

        assertThat(orderSaved.isReady()).isTrue();
        verify(orderEventListener).listen(any(OrderReadyEvent.class));
        verify(customerLoyaltyPointsApplicationService).addLoyaltyPoints(any(UUID.class), any(String.class));
    }

    @Test
    void givenInvalidOrder_whenMarkAsReady_shouldThrowOrderNotFoundException() {
        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> service.markAsReady(new OrderId().value().toLong()));
    }

    @Test
    void givenCanceledOrder_whenMarkAsReady_shouldThrowOrderStatusCannotBeChangedException() {
        order.cancel();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(() -> service.markAsReady(order.id().value().toLong()));
    }

}