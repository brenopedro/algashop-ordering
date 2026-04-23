package com.algaworks.algashop.ordering.domain.model.product;

import com.algaworks.algashop.ordering.domain.model.DomainEntityNotFoundException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_PRODUCT_NOT_FOUND;

public class ProductNotFoundException extends DomainEntityNotFoundException {
    public ProductNotFoundException() {
        super(ERROR_PRODUCT_NOT_FOUND);
    }
}
