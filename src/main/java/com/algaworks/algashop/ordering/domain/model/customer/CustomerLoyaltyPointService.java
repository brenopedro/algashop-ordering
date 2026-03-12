package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.order.OrderDoesNotBelongToCustomerException;
import com.algaworks.algashop.ordering.domain.model.DomainService;
import com.algaworks.algashop.ordering.domain.model.commons.Money;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.VALIDATION_ERROR_CUSTOMER_IS_NULL;
import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.VALIDATION_ERROR_ORDER_IS_NULL;

@DomainService
public class CustomerLoyaltyPointService {

    private static final LoyaltyPoints basePoints = new LoyaltyPoints(5);
    private static  final Money expectedAmountToGivePoints = new Money("1000");

    public void addPoints(Customer customer, Order order) {
        Objects.requireNonNull(customer, VALIDATION_ERROR_CUSTOMER_IS_NULL);
        Objects.requireNonNull(order, VALIDATION_ERROR_ORDER_IS_NULL);

        if(!customer.id().equals(order.customerId()))
            throw new OrderDoesNotBelongToCustomerException();

        if(!order.isReady())
            throw new CantAddLoyaltyPointsOrderIsNotReadyException();

        customer.addLoyaltyPoints(calculatePoints(order));
    }

    private LoyaltyPoints calculatePoints(Order order) {
        if(shouldGivePointsByAmount(order.totalAmount()))
            return new LoyaltyPoints(order.totalAmount().divide(expectedAmountToGivePoints).value().intValue()
                    * basePoints.value());

        return LoyaltyPoints.ZERO;

    }

    private boolean shouldGivePointsByAmount(Money amount) {
        return amount.compareTo(expectedAmountToGivePoints) >= 0;
    }
}
