package com.algaworks.algashop.ordering.core.domain.model.order;

import com.algaworks.algashop.ordering.core.domain.model.DomainException;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.ERROR_ORDER_DOES_NOT_BELONGS_TO_CUSTOMER;

public class OrderDoesNotBelongToCustomerException extends DomainException {

    public OrderDoesNotBelongToCustomerException() {
        super(ERROR_ORDER_DOES_NOT_BELONGS_TO_CUSTOMER);
    }
}
