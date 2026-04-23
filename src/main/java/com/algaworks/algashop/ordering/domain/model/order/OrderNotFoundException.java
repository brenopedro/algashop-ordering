package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainEntityNotFoundException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_ORDER_NOT_FOUND;

public class OrderNotFoundException extends DomainEntityNotFoundException {
    public OrderNotFoundException() {
        super(ERROR_ORDER_NOT_FOUND);
    }
}
