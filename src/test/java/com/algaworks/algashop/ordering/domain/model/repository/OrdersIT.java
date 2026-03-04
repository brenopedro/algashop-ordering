package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderStatus;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.CustomersPersistenceProvider;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.OrdersPersistenceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@Import({OrdersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class
})
class OrdersIT {

    private final Orders orders;
    private final Customers customers;

    @Autowired
    public OrdersIT(Orders orders, Customers customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @BeforeEach
    void setUp() {
        if (!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(CustomerTestDataBuilder.existingCustomer().build());
        }
    }

    @Test
    void shouldPersistAndFind() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderId orderId = order.id();
        orders.add(order);

        Optional<Order> possibleOrder = orders.ofId(orderId);

        assertThat(possibleOrder).isPresent();

        Order savedOrder = possibleOrder.get();

        assertThat(savedOrder).satisfies(
                o -> assertThat(o.id()).isEqualTo(orderId),
                o -> assertThat(o.customerId()).isEqualTo(order.customerId()),
                o -> assertThat(o.totalAmount()).isEqualTo(order.totalAmount()),
                o -> assertThat(o.totalItems()).isEqualTo(order.totalItems()),
                o -> assertThat(o.placedAt()).isEqualTo(order.placedAt()),
                o -> assertThat(o.paidAt()).isEqualTo(order.paidAt()),
                o -> assertThat(o.canceledAt()).isEqualTo(order.canceledAt()),
                o -> assertThat(o.readyAt()).isEqualTo(order.readyAt()),
                o -> assertThat(o.status()).isEqualTo(order.status()),
                o -> assertThat(o.paymentMethod()).isEqualTo(order.paymentMethod())
        );
    }

    @Test
    void shouldUpdateExistingOrder() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        orders.add(order);

        order = orders.ofId(order.id()).orElseThrow();

        order.markAsPaid();

        orders.add(order);

        order = orders.ofId(order.id()).orElseThrow();

        assertThat(order.isPaid()).isTrue();
    }

    @Test
    void shouldNotAllowStaleUpdates() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        orders.add(order);

        Order orderT1 = orders.ofId(order.id()).orElseThrow();
        Order orderT2 = orders.ofId(order.id()).orElseThrow();

        orderT1.markAsPaid();
        orders.add(orderT1);

        orderT2.cancel();

        assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(()-> orders.add(orderT2));

        Order savedOrder = orders.ofId(order.id()).orElseThrow();

        assertThat(savedOrder.canceledAt()).isNull();
        assertThat(savedOrder.paidAt()).isNotNull();

    }

    @Test
    void shouldCountExitingOrders() {
        assertThat(orders.count()).isZero();

        Order order = OrderTestDataBuilder.anOrder().build();
        orders.add(order);

        assertThat(orders.count()).isOne();
    }

    @Test
    void shouldReturnIfOrderExists() {
        Order order = OrderTestDataBuilder.anOrder().build();
        orders.add(order);

        assertThat(orders.exists(order.id())).isTrue();
        assertThat(orders.exists(new OrderId())).isFalse();
    }

    @Test
    void shouldListExistingOrderByYear() {
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());
        orders.add(OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build());

        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        List<Order> listedOrders = orders.placedByCustomerInYear(customerId, Year.now());
        assertThat(listedOrders).isNotEmpty();
        assertThat(listedOrders).hasSize(2);

         listedOrders = orders.placedByCustomerInYear(customerId, Year.now().minusYears(1));
         assertThat(listedOrders).isEmpty();
    }

    @Test
    void shouldReturnTotalAmountByCustomer() {
        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Order order2 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        orders.add(order1);
        orders.add(order2);

        Money salesQuantity = orders.totalSoldForCustomer(customerId);
        Money expectedTotalAmount = order1.totalAmount().add(order2.totalAmount());
        assertThat(salesQuantity).isEqualTo(expectedTotalAmount);
        assertThat(orders.totalSoldForCustomer(new CustomerId())).isEqualTo(Money.ZERO);
    }

    @Test
    void shouldReturnSalesQuantityByCustomer() {
        CustomerId customerId = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID;

        Order order1 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        Order order2 = OrderTestDataBuilder.anOrder().status(OrderStatus.PAID).build();
        orders.add(order1);
        orders.add(order2);

        long salesQuantity = orders.salesQuantityByCustomerInYear(customerId, Year.now());
        assertThat(salesQuantity).isEqualTo(2L);
    }
}