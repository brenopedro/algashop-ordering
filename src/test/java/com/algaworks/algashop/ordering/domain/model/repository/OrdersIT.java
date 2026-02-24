package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.OrdersPersistenceProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({OrdersPersistenceProvider.class, OrderPersistenceEntityAssembler.class, OrderPersistenceEntityDisassembler.class})
class OrdersIT {

    private Orders orders;

    @Autowired
    public OrdersIT(Orders orders) {
        this.orders = orders;
    }

    @Test
    void shouldPersistAndFind() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderId orderId = order.id();
        orders.add(order);

        Optional<Order> possibleOrder = orders.ofId(orderId);

        assertThat(possibleOrder).isPresent();

        Order savedOrder = possibleOrder.get();

        assertThat(savedOrder).satisfies(
                o -> assertThat(o.id()).isEqualTo(orderId),
                o -> assertThat(o.customerId()).isEqualTo(order.customerId()),
                o -> assertThat(o.totalAmount()).isEqualTo(order.totalAmount()),
                o -> assertThat(o.totalItems()).isEqualTo(order.totalItems()),
                o -> assertThat(o.placedAt()).isEqualTo(order.placedAt()),
                o -> assertThat(o.paidAt()).isEqualTo(order.paidAt()),
                o -> assertThat(o.canceledAt()).isEqualTo(order.canceledAt()),
                o -> assertThat(o.readyAt()).isEqualTo(order.readyAt()),
                o -> assertThat(o.status()).isEqualTo(order.status()),
                o -> assertThat(o.paymentMethod()).isEqualTo(order.paymentMethod())
        );
    }

}