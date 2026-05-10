package com.algaworks.algashop.ordering.domain.model.product;

import com.algaworks.algashop.ordering.domain.model.DomainEntityNotFoundException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_PRODUCT_NOT_FOUND;

public class ProductNotFoundException extends DomainEntityNotFoundException {
    public ProductNotFoundException() {}
    public ProductNotFoundException(ProductId productId) {
        super(String.format(ERROR_PRODUCT_NOT_FOUND, productId));
    }
}
