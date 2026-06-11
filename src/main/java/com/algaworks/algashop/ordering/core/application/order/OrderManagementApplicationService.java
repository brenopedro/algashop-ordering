package com.algaworks.algashop.ordering.core.application.order;

import com.algaworks.algashop.ordering.core.domain.model.order.Order;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.order.Orders;
import com.algaworks.algashop.ordering.core.ports.in.order.ForManagingOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderManagementApplicationService implements ForManagingOrders {

    private final Orders  orders;

    @Transactional
    public void cancel(Long rawOrderId) {
        Objects.requireNonNull(rawOrderId);
        Order order = orders.ofId(new OrderId(rawOrderId)).orElseThrow(OrderNotFoundException::new);
        order.cancel();
        orders.add(order);
    }

    @Transactional
    public void markAsPaid(Long rawOrderId) {
        Objects.requireNonNull(rawOrderId);
        Order order = orders.ofId(new OrderId(rawOrderId)).orElseThrow(OrderNotFoundException::new);
        order.markAsPaid();
        orders.add(order);
    }

    @Transactional
    public void markAsReady(Long rawOrderId) {
        Objects.requireNonNull(rawOrderId);
        Order order = orders.ofId(new OrderId(rawOrderId)).orElseThrow(OrderNotFoundException::new);
        order.markAsReady();
        orders.add(order);
    }
}
