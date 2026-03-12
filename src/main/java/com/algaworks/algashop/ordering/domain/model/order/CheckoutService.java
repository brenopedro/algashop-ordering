package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.domain.model.DomainService;
import com.algaworks.algashop.ordering.domain.model.product.Product;

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
