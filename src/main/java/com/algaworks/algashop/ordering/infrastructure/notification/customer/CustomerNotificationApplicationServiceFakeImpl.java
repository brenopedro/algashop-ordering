package com.algaworks.algashop.ordering.infrastructure.notification.customer;

import com.algaworks.algashop.ordering.core.application.customer.notification.CustomerNotificationApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerNotificationApplicationServiceFakeImpl implements CustomerNotificationApplicationService {

    @Override
    public void notifyNewRegistration(NotifyNewRegistrationInput input) {
        log.info("Welcome {}", input.firstName());
        log.info("User your email to access your account {}",  input.email());
    }
}
