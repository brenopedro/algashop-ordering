package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartItem;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartItemId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShoppingCartPersistenceEntityDisassembler {

    public ShoppingCart toDomainEntity(ShoppingCartPersistenceEntity shoppingCartPersistenceEntity) {
        return ShoppingCart.existing()
                .id(new ShoppingCartId(shoppingCartPersistenceEntity.getId()))
                .customerId(new CustomerId(shoppingCartPersistenceEntity.getCustomer().getId()))
                .totalAmount(new Money(shoppingCartPersistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(shoppingCartPersistenceEntity.getTotalItems()))
                .createdAt(shoppingCartPersistenceEntity.getCreatedAt())
                .items(toShoppingCartItemsDomainEntities(shoppingCartPersistenceEntity.getItems()))
                .build();
    }

    private Set<ShoppingCartItem> toShoppingCartItemsDomainEntities(Set<ShoppingCartItemPersistenceEntity> items) {
        return items.stream().map(this::toShoppingCartItemDomainEntity).collect(Collectors.toSet());
    }

    private ShoppingCartItem toShoppingCartItemDomainEntity(ShoppingCartItemPersistenceEntity item) {
        return ShoppingCartItem.existing()
                .id(new ShoppingCartItemId(item.getId()))
                .shoppingCartId(new ShoppingCartId(item.getShoppingCartId()))
                .productId(new ProductId(item.getProductId()))
                .productName(new ProductName(item.getName()))
                .price(new Money(item.getPrice()))
                .quantity(new Quantity(item.getQuantity()))
                .available(item.getAvailable())
                .totalAmount(new Money(item.getTotalAmount()))
                .build();
    }
}
