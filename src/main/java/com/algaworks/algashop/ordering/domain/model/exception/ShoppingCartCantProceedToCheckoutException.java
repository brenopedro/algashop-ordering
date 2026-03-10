package com.algaworks.algashop.ordering.domain.model.exception;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_VALID_ITEMS;

public class ShoppingCartCantProceedToCheckoutException extends DomainException {

    public ShoppingCartCantProceedToCheckoutException() {
        super(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_VALID_ITEMS);
    }
}
