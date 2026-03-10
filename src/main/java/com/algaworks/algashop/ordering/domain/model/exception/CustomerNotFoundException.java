package com.algaworks.algashop.ordering.domain.model.exception;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.ERROR_CUSTOMER_DOES_NOT_EXIST;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException() {
        super(ERROR_CUSTOMER_DOES_NOT_EXIST);
    }
}
