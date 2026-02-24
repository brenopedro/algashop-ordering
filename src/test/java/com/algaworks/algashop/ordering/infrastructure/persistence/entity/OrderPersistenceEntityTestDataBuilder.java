package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class OrderPersistenceEntityTestDataBuilder {

    private OrderPersistenceEntityTestDataBuilder() {}

    public static OrderPersistenceEntity.OrderPersistenceEntityBuilder existingOrder() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generatiTSID().toLong())
                .customerId(IdGenerator.generateTimeBasedUUID())
                .totalAmount(new BigDecimal("100.00"))
                .totalItems(2)
                .status(OrderStatus.DRAFT.name())
                .paymentMethod(PaymentMethod.CREDIT_CARD.name())
                .placedAt(OffsetDateTime.now())
                .paidAt(null)
                .canceledAt(null)
                .readyAt(null);
    }
}
