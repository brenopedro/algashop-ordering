package com.algaworks.algashop.ordering.application.checkout;

import com.algaworks.algashop.ordering.domain.model.commons.ZipCode;
import com.algaworks.algashop.ordering.domain.model.order.*;
import com.algaworks.algashop.ordering.domain.model.order.shipping.OriginAddressService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheckoutApplicationService {

    private final ShoppingCarts shoppingCarts;

    private final ShippingCostService shippingCostService;
    private final OriginAddressService  originAddressService;
    private final CheckoutService checkoutService;

    private final BillingInputDisassembler billingInputDisassembler;
    private final ShippingInputDisassembler shippingInputDisassembler;
    private final Orders orders;

    public String checkout(CheckoutInput input) {
        Objects.requireNonNull(input);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(input.getPaymentMethod());

        ShoppingCart shoppingCart = shoppingCarts.ofId(new ShoppingCartId(input.getShoppingCartId()))
                .orElseThrow(ShoppingCartNotFoundException::new);

        ShippingCostService.CalculationResult calculationResult = calculateShippingCost(input.getShipping());

        Order order = checkoutService.checkout(shoppingCart,
                billingInputDisassembler.toDomainModel(input.getBilling()),
                shippingInputDisassembler.toDomainModel(input.getShipping(), calculationResult),
                paymentMethod);
        orders.add(order);
        shoppingCarts.add(shoppingCart);

        return order.id().value().toString();
    }

    private ShippingCostService.CalculationResult calculateShippingCost(ShippingInput input) {
        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode(input.getAddress().getZipCode());

        return shippingCostService.calculate(new ShippingCostService.CalculationRequest(origin, destination));
    }
}
