package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceEntityAssembler {

    public OrderPersistenceEntity fromDomain(Order order) {
        return merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity to, Order from) {
        to.setId(from.id().value().toLong());
        to.setCustomerId(from.customerId().value());
        to.setTotalAmount(from.totalAmount().value());
        to.setTotalItems(from.totalItems().value());
        to.setStatus(from.status().name());
        to.setPaymentMethod(from.paymentMethod().name());
        to.setPlacedAt(from.placedAt());
        to.setPaidAt(from.paidAt());
        to.setCanceledAt(from.canceledAt());
        to.setReadyAt(from.readyAt());
        to.setVersion(from.version());
        return to;
    }
}
