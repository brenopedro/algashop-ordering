package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.DomainException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_CUSTOMER_ALREADY_HAVE_ACTIVE_CART;

public class CustomerAlreadyHaveShoppingCartException extends DomainException {

    public CustomerAlreadyHaveShoppingCartException() {
        super(ERROR_CUSTOMER_ALREADY_HAVE_ACTIVE_CART);
    }
}
