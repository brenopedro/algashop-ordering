package com.algaworks.algashop.ordering.core.domain.model.order;

import com.algaworks.algashop.ordering.core.domain.model.commons.Address;
import com.algaworks.algashop.ordering.core.domain.model.commons.Money;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.*;

@Builder(toBuilder = true)
public record Shipping(Recipient recipient, Address address, Money cost, LocalDate expectedDate) {

    public Shipping {
        Objects.requireNonNull(recipient, VALIDATION_ERROR_RECIPIENT_IS_INVALID);
        Objects.requireNonNull(address, VALIDATION_ERROR_ADDRESS_IS_INVALID);
        Objects.requireNonNull(cost, VALIDATION_ERROR_MONEY_IS_INVALID);
        Objects.requireNonNull(expectedDate, VALIDATION_ERROR_ORDER_DELIVERY_DATE_BE_INVALID);
    }
}
