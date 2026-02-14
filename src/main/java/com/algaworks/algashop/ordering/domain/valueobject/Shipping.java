package com.algaworks.algashop.ordering.domain.valueobject;

import lombok.Builder;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

@Builder(toBuilder = true)
public record Shipping(Recipient recipient, Address address, Money cost, LocalDate expectedDate) {

    public Shipping {
        Objects.requireNonNull(recipient, VALIDATION_ERROR_RECIPIENT_IS_INVALID);
        Objects.requireNonNull(address, VALIDATION_ERROR_ADDRESS_IS_INVALID);
        Objects.requireNonNull(cost, VALIDATION_ERROR_MONEY_IS_INVALID);
        Objects.requireNonNull(expectedDate, VALIDATION_ERROR_ORDER_DELIVERY_DATE_BE_INVALID);
    }
}
