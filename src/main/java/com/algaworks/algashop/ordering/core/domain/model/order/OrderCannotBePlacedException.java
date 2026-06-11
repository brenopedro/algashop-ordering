package com.algaworks.algashop.ordering.core.domain.model.order;

import com.algaworks.algashop.ordering.core.domain.model.DomainException;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.*;

public class OrderCannotBePlacedException extends DomainException {

    private OrderCannotBePlacedException(String message) {
        super(message);
    }

    public static OrderCannotBePlacedException noShippingInfo(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING, orderId.value()));
    }

    public static OrderCannotBePlacedException noBillingInfo(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO, orderId.value()));
    }

    public static OrderCannotBePlacedException noPaymentMethod(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD, orderId.value()));
    }

    public static OrderCannotBePlacedException noItems(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS, orderId.value()));
    }

}
