package com.algaworks.algashop.ordering.infrastructure.persistence.order;

import com.algaworks.algashop.ordering.core.domain.model.commons.*;
import com.algaworks.algashop.ordering.core.domain.model.order.*;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductId;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceEntityDisassembler {

    public Order toDomainEntity(OrderPersistenceEntity persistenceEntity) {
        return Order.existing()
                .id(new OrderId(persistenceEntity.getId()))
                .customerId(new CustomerId(persistenceEntity.getCustomerId()))
                .totalAmount(new Money(persistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(persistenceEntity.getTotalItems()))
                .status(OrderStatus.valueOf(persistenceEntity.getStatus()))
                .paymentMethod(PaymentMethod.valueOf(persistenceEntity.getPaymentMethod()))
                .placedAt(persistenceEntity.getPlacedAt())
                .paidAt(persistenceEntity.getPaidAt())
                .canceledAt(persistenceEntity.getCanceledAt())
                .readyAt(persistenceEntity.getReadyAt())
                .version(persistenceEntity.getVersion())
                .items(toDomainEntity(persistenceEntity.getItems()))
                .creditCardId(persistenceEntity.getCreditCardId() != null ? new CreditCardId(persistenceEntity.getCreditCardId()) : null)
                .build();
    }

    private Set<OrderItem> toDomainEntity(Set<OrderItemPersistenceEntity> items) {
        return items.stream().map(this::toDomainEntity)
                .collect(Collectors.toSet());
    }

    private OrderItem toDomainEntity(OrderItemPersistenceEntity item) {
        return OrderItem.existing()
                .id(new OrderItemId(item.getId()))
                .orderId(new OrderId(item.getOrderId()))
                .productId(new ProductId(item.getProductId()))
                .productName(new ProductName(item.getProductName()))
                .price(new Money(item.getPrice()))
                .quantity(new Quantity(item.getQuantity()))
                .totalAmount(new Money(item.getTotalAmount()))
                .build();
    }

    private Shipping toShipping(ShippingEmbeddable shipping) {
        return Shipping.builder()
                .recipient(toRecipient(shipping.getRecipient()))
                .address(toAddress(shipping.getAddress()))
                .cost(new Money(shipping.getCost()))
                .expectedDate(shipping.getExpectedDate())
                .build();
    }

    private Billing toBilling(BillingEmbeddable billing) {
        return Billing.builder()
                .fullName(new FullName(billing.getFirstName(), billing.getLastName()))
                .document(new Document(billing.getDocument()))
                .phone(new Phone(billing.getPhone()))
                .email(new Email(billing.getEmail()))
                .address(toAddress(billing.getAddress()))
                .build();
    }

    private Address toAddress(AddressEmbeddable address) {
        return Address.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(new ZipCode(address.getZipCode()))
                .build();
    }

    private Recipient toRecipient(RecipientEmbeddable recipient) {
        return Recipient.builder()
                .fullName(new FullName(recipient.getFirstName(), recipient.getLastName()))
                .document(new Document(recipient.getDocument()))
                .phone(new Phone(recipient.getPhone()))
                .build();
    }
}
