package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.product.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertWith;

class OrderFactoryTest {

    @Test
    void shouldGenerateFilledOrderThatCanBePlaced() {
        Shipping shipping = OrderTestDataBuilder.aShipping();
        Billing billing = OrderTestDataBuilder.aBilling();

        Product product = ProductTestDataBuilder.aProduct().build();
        PaymentMethod gateway = PaymentMethod.GATEWAY_BALANCE;

        Quantity quantity = new Quantity(2);
        CustomerId customerId = new CustomerId();

        Order order = OrderFactory.filled(customerId, shipping, billing, gateway, product, quantity);

        assertWith(order,
                o -> assertThat(o.shipping()).isEqualTo(shipping),
                o -> assertThat(o.billing()).isEqualTo(billing),
                o -> assertThat(o.paymentMethod()).isEqualTo(gateway),
                o -> assertThat(o.items()).isNotEmpty(),
                o -> assertThat(customerId).isEqualTo(o.customerId()),
                o -> assertThat(o.isDraft()).isTrue()
                );

        order.place();

        assertThat(order.isPlaced()).isTrue();
    }

}