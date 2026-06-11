package com.algaworks.algashop.ordering.core.application.order.query;

import com.algaworks.algashop.ordering.core.application.AbstractApplicationIT;
import com.algaworks.algashop.ordering.core.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.core.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.core.domain.model.order.Order;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderStatus;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.core.domain.model.order.Orders;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderQueryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

class OrderQueryServiceIT extends AbstractApplicationIT {

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private Orders orders;

    @Autowired
    private Customers customers;

    @Autowired
    private OrderQueryServiceImpl orderQueryServiceImpl;

    @Test
    void shouldFindById() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer);

        Order order = OrderTestDataBuilder.anOrder().customerId(customer.id()).build();
        orders.add(order);

        OrderDetailOutput output = orderQueryService.findById(order.id().toString());

        assertThat(output)
                .extracting(
                        OrderDetailOutput::getId,
                        OrderDetailOutput::getTotalAmount
                ).containsExactly(
                        order.id().toString(),
                        order.totalAmount().value()
                );
    }

    @Test
    void shouldFilterByPage() {
        Customer customer = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer.id()).build());

        Page<OrderSummaryOutput> page = orderQueryServiceImpl.filter(new OrderFilter(3, 0));
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumberOfElements()).isEqualTo(3);
    }

    @Test
    void shouldFilterByCustomerId() {
        Customer customer1 = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer1);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer1.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer1.id()).build());

        Customer customer2 = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customers.add(customer2);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer2.id()).build());

        OrderFilter filter = new OrderFilter();
        filter.setCustomerId(customer1.id().value());

        Page<OrderSummaryOutput> page = orderQueryServiceImpl.filter(filter);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    void shouldFilterByMultipleParams() {
        Customer customer1 = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer1);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer1.id()).build());
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer1.id()).build();
        orders.add(order1);

        Customer customer2 = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customers.add(customer2);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer2.id()).build());

        OrderFilter filter = new OrderFilter();
        filter.setCustomerId(customer1.id().value());
        filter.setStatus(OrderStatus.PLACED.toString().toLowerCase());
        filter.setTotalAmountFrom(order1.totalAmount().value());

        Page<OrderSummaryOutput> page = orderQueryServiceImpl.filter(filter);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getNumberOfElements()).isEqualTo(1);
    }

    @Test
    void givenInvalidOrderId_whenFiltered_shouldReturnEmptyPage() {
        Customer customer1 = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer1);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer1.id()).build());
        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer1.id()).build();
        orders.add(order1);

        Customer customer2 = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customers.add(customer2);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer2.id()).build());

        OrderFilter filter = new OrderFilter();
        filter.setOrderId("ABC");

        Page<OrderSummaryOutput> page = orderQueryServiceImpl.filter(filter);
        assertThat(page.getTotalPages()).isEqualTo(0);
        assertThat(page.getTotalElements()).isEqualTo(0);
        assertThat(page.getNumberOfElements()).isEqualTo(0);
    }

    @Test
    void shouldOrderByStatus() {
        Customer customer1 = CustomerTestDataBuilder.existingCustomer().build();
        customers.add(customer1);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).withItems(false).customerId(customer1.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).customerId(customer1.id()).build());

        Customer customer2 = CustomerTestDataBuilder.existingCustomer().id(new CustomerId()).build();
        customers.add(customer2);

        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.READY).customerId(customer2.id()).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.CANCELED).customerId(customer2.id()).build());

        OrderFilter filter = new OrderFilter();
        filter.setSortByProperty(OrderFilter.SortType.STATUS);
        filter.setSortDirection(Sort.Direction.ASC);

        Page<OrderSummaryOutput> page = orderQueryServiceImpl.filter(filter);
        assertThat(page.getContent().getFirst().getStatus()).isEqualTo(OrderStatus.CANCELED.toString());
    }

}