package com.algaworks.algashop.ordering.domain.model.exception;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.ERROR_ORDER_DOES_NOT_BELONGS_TO_CUSTOMER;

public class OrderDoesNotBelongsToCustomerException extends DomainException {

    public OrderDoesNotBelongsToCustomerException() {
        super(ERROR_ORDER_DOES_NOT_BELONGS_TO_CUSTOMER);
    }
}
