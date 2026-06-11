package com.algaworks.algashop.ordering.infrastructure.listener.order;

import com.algaworks.algashop.ordering.core.domain.model.order.OrderCanceledEvent;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderPaidEvent;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderPlacedEvent;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderReadyEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    @EventListener
    public void listen(OrderPlacedEvent event) {
        log.info("OrderPlacedEvent listen: {}", event);
    }

    @EventListener
    public void listen(OrderPaidEvent event) {
        log.info("OrderPaidEvent listen: {}", event);
    }

    @EventListener
    public void listen(OrderReadyEvent event) {
        log.info("OrderReadyEvent listen: {}", event);
    }

    @EventListener
    public void listen(OrderCanceledEvent event) {
        log.info("OrderCanceledEvent listen: {}", event);
    }
}
