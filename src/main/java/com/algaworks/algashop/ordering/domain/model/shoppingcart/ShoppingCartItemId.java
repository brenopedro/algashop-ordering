package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.IdGenerator;

import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.VALIDATION_ERROR_SHOPPING_CART_ITEM_ID_IS_INVALID;

public record ShoppingCartItemId(UUID value) {

    public ShoppingCartItemId {
        Objects.requireNonNull(value, VALIDATION_ERROR_SHOPPING_CART_ITEM_ID_IS_INVALID);
    }

    public ShoppingCartItemId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
