package com.algaworks.algashop.ordering.core.domain.model.order;

import com.algaworks.algashop.ordering.core.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderIsCanceledTest {

    @Test
    void givenCanceledOrder_whenIsCanceled_shouldReturnTrue() {
        Order order = Order.draft(new CustomerId());
        assertThat(order.isCanceled()).isFalse();
        order.cancel();
        assertThat(order.isCanceled()).isTrue();
    }

    @Test
    void givenNonCanceledOrder_whenIsCanceled_shouldReturnFalse() {
        Order order = Order.draft(new CustomerId());
        order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(2));

        assertThat(order.isCanceled()).isFalse();
    }

    @Test
    void givenOrderInAnyOtherStatus_whenIsCanceled_shouldReturnFalse() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        assertThat(order.isCanceled()).isFalse();
    }
}