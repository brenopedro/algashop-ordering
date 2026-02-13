package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import com.algaworks.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertWith;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTest {

    @Test
    void shouldGenerate() {
        Order order = Order.draft(new CustomerId());

        assertNotNull(order.id());
    }

    @Test
    void shouldAddItem() {
        Order order = Order.draft(new CustomerId());

        order.addItem(new ProductId(),
                new ProductName("Product"), new Money("10"), new Quantity(2));

        assertNotNull(order.items());
        assertThat(order.items()).hasSize(1);
    }

    @Test
    void shouldGenerateExceptionWhenTryToChangeItemSet() {
        Order order = Order.draft(new CustomerId());

        order.addItem(new ProductId(),
                new ProductName("Product"), new Money("10"), new Quantity(2));

        Set<OrderItem> items = order.items();

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);

    }

    @Test
    void shouldCalculateTotals() {
        Order order = Order.draft(new CustomerId());

        order.addItem(new ProductId(),
                new ProductName("Product"), new Money("100"), new Quantity(2));

        order.addItem(new ProductId(),
                new ProductName("Product 2"), new Money("20"), new Quantity(5));


        assertThat(order.totalAmount()).isEqualTo(new Money("300"));
        assertThat(order.totalItems()).isEqualTo(new Quantity(7));
    }

    @Test
    public void givenDraftOrder_whenPlace_shouldChangeStatusToPlaced() {
        Order order = OrderTestDataBuilder.anOrder().build();
        order.place();
        assertThat(order.isPlaced()).isTrue();
    }

    @Test
    public void givenPlacedOrder_whenMarkAsPaid_shouldChangeStatusToPaid() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        order.markAsPaid();
        assertThat(order.isPaid()).isTrue();
        assertThat(order.paidAt()).isNotNull();
    }

    @Test
    public void givenPlacedOrder_whenPlace_shouldThrowException() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();

        assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::place);
    }

    @Test
    void givenDraftOrder_whenChangePaymentMethod_shouldAllowChange() {
        Order order = Order.draft(new CustomerId());

        order.changePaymentMethod(PaymentMethod.CREDIT_CARD);

        assertThat(order.paymentMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
    }

    @Test
    void givenDraftOrder_whenChangeBillingInfo_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();

        Address address = Address.builder()
                .street("Main Street")
                .number("123")
                .neighborhood("Downtown")
                .city("Anytown")
                .state("State")
                .zipCode(new ZipCode("12345"))
                .build();

        BillingInfo billingInfo = BillingInfo.builder()
                .document(new Document("123456789"))
                .phone(new Phone("1234567890"))
                .fullName(new FullName("John", "Doe"))
                .address(address)
                .build();

        order.changeBillingInfo(billingInfo);

        assertThat(order.billing()).isEqualTo(billingInfo);
    }

    @Test
    void givenDraftOrder_whenChangeShippingInfo_shouldAllowChange() {
        Order order = Order.draft(new CustomerId());

        Address address = Address.builder()
                .street("Main Street")
                .number("123")
                .neighborhood("Downtown")
                .city("Anytown")
                .state("State")
                .zipCode(new ZipCode("12345"))
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .document(new Document("123456789"))
                .phone(new Phone("1234567890"))
                .fullName(new FullName("John", "Doe"))
                .address(address)
                .build();

        order.changeShippingInfo(shippingInfo, Money.ZERO, LocalDate.now().plusDays(1));

        assertWith(order,
                o -> assertThat(o.shipping()).isEqualTo(shippingInfo),
                o -> assertThat(o.shippingCost()).isEqualTo(Money.ZERO),
                o -> assertThat(o.expectedDeliveryDate()).isEqualTo(LocalDate.now().plusDays(1))
        );
    }

    @Test
    void givenDraftOrderAndDeliveryDateInPast_whenChangeShippingInfo_shouldThrowException() {
        Order order = Order.draft(new CustomerId());

        Address address = Address.builder()
                .street("Main Street")
                .number("123")
                .neighborhood("Downtown")
                .city("Anytown")
                .state("State")
                .zipCode(new ZipCode("12345"))
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .document(new Document("123456789"))
                .phone(new Phone("1234567890"))
                .fullName(new FullName("John", "Doe"))
                .address(address)
                .build();

        assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                .isThrownBy(() -> order.changeShippingInfo(shippingInfo, Money.ZERO,
                        LocalDate.now().minusDays(2)));
    }

    @Test
    void givenDraftOrder_whenChangeItem_shouldRecalculate() {
        Order order = Order.draft(new CustomerId());

        order.addItem(new ProductId(),
                new ProductName("Product"), new Money("100"), new Quantity(2));

        OrderItem item = order.items().iterator().next();

        order.changeItemQuantity(item.id(), new Quantity(5));

        assertThat(order.totalAmount()).isEqualTo(new Money("500"));
        assertThat(order.totalItems()).isEqualTo(new Quantity(5));
    }

}