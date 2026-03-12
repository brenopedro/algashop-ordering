package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.VALIDATION_ERROR_SHOPPING_CART_ID_IS_INVALID;

public record ShoppingCartId(UUID value) {

    public ShoppingCartId {
        Objects.requireNonNull(value, VALIDATION_ERROR_SHOPPING_CART_ID_IS_INVALID);
    }

    public ShoppingCartId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
