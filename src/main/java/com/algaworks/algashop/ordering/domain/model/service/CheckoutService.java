package com.algaworks.algashop.ordering.domain.model.service;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.exception.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.utility.DomainService;
import com.algaworks.algashop.ordering.domain.model.valueobject.Billing;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.Shipping;

@DomainService
public class CheckoutService {

    public Order checkout(ShoppingCart shoppingCart, Billing billing,
                          Shipping shipping, PaymentMethod paymentMethod) {

        if(shoppingCart.isEmpty() || shoppingCart.containsUnavailableItems())
            throw new ShoppingCartCantProceedToCheckoutException();

        Order order = Order.draft(shoppingCart.customerId());
        order.changeBilling(billing);
        order.changeShipping(shipping);
        order.changePaymentMethod(paymentMethod);

        shoppingCart.items().forEach(item -> {
            order.addItem(new Product(item.productId(), item.name(), item.price(), item.isAvailable()),
                    item.quantity());
        });

        order.place();
        shoppingCart.empty();

        return order;
    }
}
