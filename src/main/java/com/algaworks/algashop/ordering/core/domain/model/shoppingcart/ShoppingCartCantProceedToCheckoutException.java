package com.algaworks.algashop.ordering.core.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.core.domain.model.DomainException;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_VALID_ITEMS;

public class ShoppingCartCantProceedToCheckoutException extends DomainException {

    public ShoppingCartCantProceedToCheckoutException() {
        super(ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_VALID_ITEMS);
    }
}
