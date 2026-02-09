package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderItemTest {

    @Test
    void shouldGenerate() {
        OrderItem orderItem = OrderItem.brandNew()
                .productId(new ProductId())
                .orderId(new OrderId())
                .productName(new ProductName("Test Product"))
                .price(Money.ZERO)
                .quantity(new Quantity(1))
                .build();

        assertNotNull(orderItem.id());
        assertNotNull(orderItem.orderId());
        assertNotNull(orderItem.productId());
        assertEquals("Test Product", orderItem.productName().value());
        assertEquals(Money.ZERO, orderItem.price());
        assertEquals(new Quantity(1), orderItem.quantity());
    }

}