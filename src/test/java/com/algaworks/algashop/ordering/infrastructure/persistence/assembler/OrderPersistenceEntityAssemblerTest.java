package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderPersistenceEntityAssemblerTest {

    private final OrderPersistenceEntityAssembler assembler = new OrderPersistenceEntityAssembler();

    @Test
    void shouldConvertToDomain() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(order);


        assertThat(persistenceEntity).satisfies(
            pe -> assertThat(pe.getId()).isEqualTo(order.id().value().toLong()),
            pe -> assertThat(pe.getCustomerId()).isEqualTo(order.customerId().value()),
            pe -> assertThat(pe.getTotalAmount()).isEqualTo(order.totalAmount().value()),
            pe -> assertThat(pe.getTotalItems()).isEqualTo(order.totalItems().value()),
            pe -> assertThat(pe.getStatus()).isEqualTo(order.status().name()),
            pe -> assertThat(pe.getPaymentMethod()).isEqualTo(order.paymentMethod().name()),
            pe -> assertThat(pe.getPlacedAt()).isEqualTo(order.placedAt()),
            pe -> assertThat(pe.getPaidAt()).isEqualTo(order.paidAt()),
            pe -> assertThat(pe.getCanceledAt()).isEqualTo(order.canceledAt()),
            pe -> assertThat(pe.getReadyAt()).isEqualTo(order.readyAt())
        );
    }

    @Test
    void shouldMerge() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity persistenceEntity = new OrderPersistenceEntity();

        assembler.merge(persistenceEntity, order);

        assertThat(persistenceEntity).satisfies(
                pe -> assertThat(pe.getId()).isEqualTo(order.id().value().toLong()),
                pe -> assertThat(pe.getCustomerId()).isEqualTo(order.customerId().value()),
                pe -> assertThat(pe.getTotalAmount()).isEqualTo(order.totalAmount().value()),
                pe -> assertThat(pe.getTotalItems()).isEqualTo(order.totalItems().value()),
                pe -> assertThat(pe.getStatus()).isEqualTo(order.status().name()),
                pe -> assertThat(pe.getPaymentMethod()).isEqualTo(order.paymentMethod().name()),
                pe -> assertThat(pe.getPlacedAt()).isEqualTo(order.placedAt()),
                pe -> assertThat(pe.getPaidAt()).isEqualTo(order.paidAt()),
                pe -> assertThat(pe.getCanceledAt()).isEqualTo(order.canceledAt()),
                pe -> assertThat(pe.getReadyAt()).isEqualTo(order.readyAt())
        );
    }

}