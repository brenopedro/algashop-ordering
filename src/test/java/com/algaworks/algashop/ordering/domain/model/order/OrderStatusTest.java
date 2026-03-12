package com.algaworks.algashop.ordering.domain.model.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderStatusTest {

    @Test
    void shouldChangeTo() {
        assertThat(OrderStatus.DRAFT.canChangeTo(OrderStatus.PLACED)).isTrue();
        assertThat(OrderStatus.PLACED.canChangeTo(OrderStatus.PAID)).isTrue();
        assertThat(OrderStatus.PAID.canChangeTo(OrderStatus.READY)).isTrue();
        assertThat(OrderStatus.DRAFT.canChangeTo(OrderStatus.CANCELED)).isTrue();
        assertThat(OrderStatus.PLACED.canChangeTo(OrderStatus.CANCELED)).isTrue();
        assertThat(OrderStatus.PAID.canChangeTo(OrderStatus.CANCELED)).isTrue();
        assertThat(OrderStatus.READY.canChangeTo(OrderStatus.CANCELED)).isTrue();
    }

    @Test
    void shouldNotChangeTo() {
        assertThat(OrderStatus.DRAFT.canNotChangeTo(OrderStatus.READY)).isTrue();
    }

}