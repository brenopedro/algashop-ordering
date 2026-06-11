package com.algaworks.algashop.ordering.core.domain.model.order;

import com.algaworks.algashop.ordering.core.domain.model.commons.Money;
import com.algaworks.algashop.ordering.core.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.ShoppingCartCantProceedToCheckoutException;
import com.algaworks.algashop.ordering.core.domain.model.DomainService;
import com.algaworks.algashop.ordering.core.domain.model.product.Product;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class CheckoutService {

    private final CustomerHaveFreeShippingSpecification customerHaveFreeShippingSpecification;

    public Order checkout(Customer customer, ShoppingCart shoppingCart, Billing billing,
                          Shipping shipping, PaymentMethod paymentMethod, CreditCardId creditCardId) {

        if(shoppingCart.isEmpty() || shoppingCart.containsUnavailableItems())
            throw new ShoppingCartCantProceedToCheckoutException();

        Order order = Order.draft(shoppingCart.customerId());
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod, creditCardId);

        if(haveFreeShipping(customer)) {
            order.changeShipping(shipping.toBuilder().cost(Money.ZERO).build());
        } else {
            order.changeShipping(shipping);
        }

        shoppingCart.items().forEach(item -> order.addItem(new Product(item.productId(), item.name(),
                        item.price(), item.isAvailable()),
                item.quantity()));

        order.place();
        shoppingCart.empty();

        return order;
    }

    private boolean haveFreeShipping(Customer customer) {
        return customerHaveFreeShippingSpecification.isSatisfiedBy(customer);
    }

}
