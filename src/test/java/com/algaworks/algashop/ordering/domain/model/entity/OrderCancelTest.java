package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.exception.OrderStatusCannotBeChangedException;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertWith;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class OrderCancelTest {

    @Test
    void givenEmptyOrder_whenCancel_shouldAllow() {
        Order order = Order.draft(new CustomerId());

        order.cancel();

        assertWith(order,
                (i) -> assertThat(i.status()).isEqualTo(OrderStatus.CANCELED),
                (i) -> assertThat(i.isCanceled()).isTrue(),
                (i) -> assertThat(i.canceledAt()).isNotNull()
        );
    }

    @Test
    void givenFilledOrder_whenCancel_shouldAllow() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        order.cancel();

        assertWith(order,
                (i) -> assertThat(i.status()).isEqualTo(OrderStatus.CANCELED),
                (i) -> assertThat(i.isCanceled()).isTrue(),
                (i) -> assertThat(i.canceledAt()).isNotNull()
        );
    }

    @Test
    void givenCanceledOrder_whenCancelAgain_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).build();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::cancel);

        assertWith(order,
                (i) -> assertThat(i.status()).isEqualTo(OrderStatus.CANCELED),
                (i) -> assertThat(i.isCanceled()).isTrue(),
                (i) -> assertThat(i.canceledAt()).isNotNull()
        );
    }

}