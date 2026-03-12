package com.algaworks.algashop.ordering.domain.model.order;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    DRAFT,
    PLACED(DRAFT),
    PAID(PLACED),
    READY(PAID),
    CANCELED(DRAFT, PLACED, PAID, READY);

    private final List<OrderStatus> previousStatuses;

    OrderStatus(OrderStatus... previousStatuses) {
        this.previousStatuses = Arrays.asList(previousStatuses);
    }

    public boolean canChangeTo(OrderStatus newStatus) {
        return newStatus.previousStatuses.contains(this);
    }

    public boolean canNotChangeTo(OrderStatus newStatus) {
        return !canChangeTo(newStatus);
    }
}
