package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.order.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.order.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.product.ProductTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerLoyaltyPointServiceTest {

    CustomerLoyaltyPointsService customerLoyaltyPointService = new CustomerLoyaltyPointsService();

    @Test
    void givenValidCustomerAndOrder_whenAddingPoints_ShouldAccumulate() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.READY).build();

        customerLoyaltyPointService.addPoints(customer, order);

        assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void givenValidCustomerAndOrderWithLowTotalAmount_whenAddingPoints_ShouldNotAccumulate() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        Product product = ProductTestDataBuilder.aProductAltRamMemory().build();

        Order order = OrderTestDataBuilder.anOrder()
                .withItems(false)
                .status(OrderStatus.DRAFT).build();
        order.addItem(product, new Quantity(1));
        order.place();
        order.markAsPaid();
        order.markAsReady();

        customerLoyaltyPointService.addPoints(customer, order);

        assertThat(customer.loyaltyPoints()).isEqualTo(LoyaltyPoints.ZERO);
    }

}