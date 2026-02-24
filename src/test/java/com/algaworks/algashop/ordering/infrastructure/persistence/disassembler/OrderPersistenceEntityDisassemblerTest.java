package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderPersistenceEntityDisassemblerTest {

    private final OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    void shouldConvertFromPersistence() {
        OrderPersistenceEntity persistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();
        Order domainEntity = disassembler.toDomainEntity(persistenceEntity);
        assertThat(domainEntity).satisfies(
                d -> assertThat(d.id().value().toLong()).isEqualTo(persistenceEntity.getId()),
                d -> assertThat(d.customerId().value()).isEqualTo(persistenceEntity.getCustomerId()),
                d -> assertThat(d.totalAmount().value()).isEqualTo(persistenceEntity.getTotalAmount()),
                d -> assertThat(d.totalItems().value()).isEqualTo(persistenceEntity.getTotalItems()),
                d -> assertThat(d.placedAt()).isEqualTo(persistenceEntity.getPlacedAt()),
                d -> assertThat(d.paidAt()).isEqualTo(persistenceEntity.getPaidAt()),
                d -> assertThat(d.canceledAt()).isEqualTo(persistenceEntity.getCanceledAt()),
                d -> assertThat(d.readyAt()).isEqualTo(persistenceEntity.getReadyAt()),
                d -> assertThat(d.status().name()).isEqualTo(persistenceEntity.getStatus()),
                d -> assertThat(d.paymentMethod().name()).isEqualTo(persistenceEntity.getPaymentMethod())
        );
    }

}