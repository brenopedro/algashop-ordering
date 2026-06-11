package com.algaworks.algashop.ordering.core.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.core.domain.model.DomainException;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductId;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT;

public class ShoppingCartItemIncompatibleProductException extends DomainException {
    public ShoppingCartItemIncompatibleProductException(ShoppingCartItemId id, ProductId productId) {
        super(String.format(ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT, id, productId));
    }
}
