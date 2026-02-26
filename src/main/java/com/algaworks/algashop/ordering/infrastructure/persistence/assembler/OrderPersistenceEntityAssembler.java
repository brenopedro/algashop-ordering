package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.valueobject.Address;
import com.algaworks.algashop.ordering.domain.model.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.valueobject.Recipient;
import com.algaworks.algashop.ordering.domain.model.valueobject.Shipping;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceEntityAssembler {

    public OrderPersistenceEntity fromDomain(Order order) {
        return merge(new OrderPersistenceEntity(), order);
    }

    public OrderPersistenceEntity merge(OrderPersistenceEntity to, Order from) {
        to.setId(from.id().value().toLong());
        to.setCustomerId(from.customerId().value());
        to.setTotalAmount(from.totalAmount().value());
        to.setTotalItems(from.totalItems().value());
        to.setStatus(from.status().name());
        to.setPaymentMethod(from.paymentMethod().name());
        to.setPlacedAt(from.placedAt());
        to.setPaidAt(from.paidAt());
        to.setCanceledAt(from.canceledAt());
        to.setReadyAt(from.readyAt());
        to.setVersion(from.version());
        to.setBilling(toBillingEmbeddable(from.billing()));
        to.setShipping(toShippingEmbeddable(from.shipping()));
        return to;
    }

    private BillingEmbeddable toBillingEmbeddable(Billing billing) {
        if (billing == null) {
            return null;
        }
        return BillingEmbeddable.builder()
                .firstName(billing.fullName().firstName())
                .lastName(billing.fullName().lastName())
                .document(billing.document().value())
                .phone(billing.phone().value())
                .address(toAddressEmbeddable(billing.address()))
                .build();
    }

    private ShippingEmbeddable toShippingEmbeddable(Shipping shipping) {
        if (shipping == null) {
            return null;
        }
        return ShippingEmbeddable.builder()
                .cost(shipping.cost().value())
                .expectedDate(shipping.expectedDate())
                .address(toAddressEmbeddable(shipping.address()))
                .recipient(toRecipientEmbeddable(shipping.recipient()))
                .build();
    }

    private RecipientEmbeddable toRecipientEmbeddable(Recipient recipient) {
        if (recipient == null) {
            return null;
        }
        return RecipientEmbeddable.builder()
                .firstName(recipient.fullName().firstName())
                .lastName(recipient.fullName().lastName())
                .document(recipient.document().value())
                .phone(recipient.phone().value())
                .build();
    }

    private AddressEmbeddable toAddressEmbeddable(Address address) {
        if (address == null) {
            return null;
        }
        return AddressEmbeddable.builder()
                .street(address.street())
                .number(address.number())
                .complement(address.complement())
                .neighborhood(address.neighborhood())
                .city(address.city())
                .state(address.state())
                .zipCode(address.zipCode().value())
                .build();
    }
}
