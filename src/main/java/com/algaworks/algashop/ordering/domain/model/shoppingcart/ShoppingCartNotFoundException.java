package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.DomainException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_SHOPPING_CART_NOT_FOUND;

public class ShoppingCartNotFoundException extends DomainException {
    public ShoppingCartNotFoundException() {
        super(ERROR_SHOPPING_CART_NOT_FOUND);
    }
}
