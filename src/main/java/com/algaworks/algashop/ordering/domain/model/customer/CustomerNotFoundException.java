package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.DomainException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_CUSTOMER_DOES_NOT_EXIST;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException() {
        super(ERROR_CUSTOMER_DOES_NOT_EXIST);
    }
}
