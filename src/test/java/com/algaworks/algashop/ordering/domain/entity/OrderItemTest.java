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
                .orderId(new OrderId())
                .product(ProductTestDataBuilder.aProduct().build())
                .quantity(new Quantity(1))
                .build();

        assertNotNull(orderItem.id());
        assertNotNull(orderItem.orderId());
        assertNotNull(orderItem.productId());
        assertEquals("Desktop", orderItem.productName().value());
        assertEquals(new Money("3000"), orderItem.price());
        assertEquals(new Quantity(1), orderItem.quantity());
    }

}