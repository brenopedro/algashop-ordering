package com.algaworks.algashop.ordering.domain.model.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OrderMarkAsReadyTest {

    @Test
    void givenPaidOrder_whenMarkAsReady_shouldUpdateStatusAndTimestamp() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();

        order.markAsReady();

        assertWith(order,
                (o) -> assertThat(o.status()).isEqualTo(OrderStatus.READY),
                (o) -> assertThat(o.readyAt()).isNotNull()
        );
    }

    @Test
    void givenDraftOrder_whenMarkAsReady_shouldThrowExceptionAndNotChangeState() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        assertWith(order,
                (o) -> assertThat(o.status()).isEqualTo(OrderStatus.DRAFT),
                (o) -> assertThat(o.readyAt()).isNull()
        );
    }

    @Test
    void givenPlacedOrder_whenMarkAsReady_shouldThrowExceptionAndNotChangeState() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        assertWith(order,
                (o) -> assertThat(o.status()).isEqualTo(OrderStatus.PLACED),
                (o) -> assertThat(o.readyAt()).isNull()
        );
    }

    @Test
    void givenReadyOrder_whenMarkAsReady_shouldThrowExceptionAndNotChangeState() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        assertWith(order,
                (o) -> assertThat(o.status()).isEqualTo(OrderStatus.READY),
                (o) -> assertThat(o.readyAt()).isNotNull()
        );
    }
}
