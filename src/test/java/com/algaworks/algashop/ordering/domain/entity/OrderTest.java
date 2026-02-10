package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {

    @Test
    void shouldGenerate() {
        Order order = Order.draft(new CustomerId());

        assertNotNull(order.id());
    }

    @Test
    void shouldAddItem() {
        Order order = Order.draft(new CustomerId());

        order.addItem(new ProductId(),
                new ProductName("Product"), new Money("10"), new Quantity(2));

        assertNotNull(order.items());
        assertThat(order.items()).hasSize(1);
    }

    @Test
    void shouldGenerateExceptionWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());

        order.addItem(new ProductId(),
                new ProductName("Product"), new Money("10"), new Quantity(2));

        Set<OrderItem> items = order.items();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);

    }

    @Test
    void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());

        order.addItem(new ProductId(),
                new ProductName("Product"), new Money("100"), new Quantity(2));

        order.addItem(new ProductId(),
                new ProductName("Product 2"), new Money("20"), new Quantity(5));


        assertThat(order.totalAmount()).isEqualTo(new Money("300"));
        assertThat(order.totalItems()).isEqualTo(new Quantity(7));
    }

}