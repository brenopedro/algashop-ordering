package com.algaworks.algashop.ordering.infrastructure.listener.customer;


import com.algaworks.algashop.ordering.core.application.customer.loyaltypoints.CustomerLoyaltyPointsApplicationService;
import com.algaworks.algashop.ordering.core.application.customer.notification.CustomerNotificationApplicationService;
import com.algaworks.algashop.ordering.core.domain.model.commons.Email;
import com.algaworks.algashop.ordering.core.domain.model.commons.FullName;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerRegisteredEvent;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderReadyEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CustomerEventListenerIT {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @MockitoSpyBean
    private CustomerEventListener customerEventListener;

    @MockitoBean
    private CustomerLoyaltyPointsApplicationService customerLoyaltyPointsApplicationService;

    @MockitoBean
    private CustomerNotificationApplicationService customerNotificationApplicationService;

    @Test
    void shouldListenOrderReadyEvent() {
        applicationEventPublisher.publishEvent(new OrderReadyEvent(
                new OrderId(), new CustomerId(), OffsetDateTime.now()));

        verify(customerEventListener).listen(any(OrderReadyEvent.class));
        verify(customerLoyaltyPointsApplicationService).addLoyaltyPoints(any(UUID.class), any(String.class));
    }

    @Test
    void shouldListenCustomerRegisteredEvent() {
        applicationEventPublisher.publishEvent(new CustomerRegisteredEvent(
                new CustomerId(), OffsetDateTime.now(), new FullName("John", "Doe"), new Email("john@gmail.com")));

        verify(customerEventListener).listen(any(CustomerRegisteredEvent.class));
        verify(customerNotificationApplicationService).notifyNewRegistration(
                any(CustomerNotificationApplicationService.NotifyNewRegistrationInput.class));
    }

}