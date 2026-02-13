package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

    private CustomerId customerId = new CustomerId();

    private PaymentMethod paymentMethod = PaymentMethod.GATEWAY_BALANCE;

    private Money shippingCost = new Money("10");
    private LocalDate expectedDeliveryDate = LocalDate.now().plusWeeks(1);

    private ShippingInfo shippingInfo = aShippingInfo();
    private BillingInfo billingInfo = aBillingInfo();

    private boolean withItems = true;

    private OrderStatus status = OrderStatus.DRAFT;

    private OrderTestDataBuilder() {

    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        order.changeShippingInfo(shippingInfo, shippingCost, expectedDeliveryDate);
        order.changeBillingInfo(billingInfo);
        order.changePaymentMethod(paymentMethod);

        if (withItems) {
            order.addItem(new ProductId(), new ProductName("Notebook"),
                    new Money("1000"), new Quantity(1));
            order.addItem(new ProductId(), new ProductName("4GB RAM"),
                    new Money("200"), new Quantity(5));
        }

        switch (this.status) {
            case DRAFT -> {
            }
            case PLACED -> {
                order.place();
            }
            case PAID -> {
                order.place();
                order.markAsPaid();
            }
            case READY -> {
                order.place();
                order.markAsPaid();
                order.markAsReady();
            }
            case CANCELED -> {
            }
        }

        return order;
    }

    public static ShippingInfo aShippingInfo() {
        return ShippingInfo.builder()
                .document(new Document("123456789"))
                .phone(new Phone("1234567890"))
                .fullName(new FullName("John", "Doe"))
                .address(anAddress())
                .build();
    }

    public static BillingInfo aBillingInfo() {
        return BillingInfo.builder()
                .document(new Document("123456789"))
                .phone(new Phone("1234567890"))
                .fullName(new FullName("John", "Doe"))
                .address(anAddress())
                .build();
    }

    public static Address anAddress() {
        return Address.builder()
                .street("Main Street")
                .number("123")
                .neighborhood("Downtown")
                .city("Anytown")
                .state("State")
                .zipCode(new ZipCode("12345"))
                .build();
    }

    public OrderTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderTestDataBuilder shippingCost(Money shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public OrderTestDataBuilder expectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public OrderTestDataBuilder shippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
        return this;
    }

    public OrderTestDataBuilder billingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
        return this;
    }

    public OrderTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

    public OrderTestDataBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }
}
