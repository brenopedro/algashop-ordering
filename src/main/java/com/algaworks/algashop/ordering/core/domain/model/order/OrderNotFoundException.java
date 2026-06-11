package com.algaworks.algashop.ordering.core.domain.model.order;

import com.algaworks.algashop.ordering.core.domain.model.DomainEntityNotFoundException;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.ERROR_ORDER_NOT_FOUND;

public class OrderNotFoundException extends DomainEntityNotFoundException {
    public OrderNotFoundException() {
        super(ERROR_ORDER_NOT_FOUND);
    }
}
