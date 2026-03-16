package com.algaworks.algashop.ordering.application.customer.loyaltypoints;

import com.algaworks.algashop.ordering.domain.model.customer.*;
import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.OrderNotFoundException;
import com.algaworks.algashop.ordering.domain.model.order.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerLoyaltyPointsApplicationService {

    private final CustomerLoyaltyPointsService customerLoyaltyPointsService;
    private final Orders orders;
    private final Customers customers;

    @Transactional
    public void addLoyaltyPoints(UUID rawCustomerId, String rawOrderId) {
        Order order = orders.ofId(new OrderId(rawOrderId)).orElseThrow(OrderNotFoundException::new);
        Customer customer = customers.ofId(new CustomerId(rawCustomerId)).orElseThrow(CustomerNotFoundException::new);

        customerLoyaltyPointsService.addPoints(customer, order);

        customers.add(customer);
    }
}
