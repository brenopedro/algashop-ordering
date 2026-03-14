package com.algaworks.algashop.ordering.application.checkout;

import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.domain.model.order.Orders;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.product.ProductTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class BuyNowApplicationServiceIT {

    @Autowired
    private BuyNowApplicationService service;

    @Autowired
    private Orders orders;

    @Autowired
    private Customers customers;

    @MockitoBean
    private ProductCatalogService  productCatalogService;

    @MockitoBean
    private ShippingCostService shippingCostService;
    @Autowired
    private BuyNowApplicationService buyNowApplicationService;

    @BeforeEach
    void setUp() {
        if (!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(CustomerTestDataBuilder.existingCustomer().build());
        }
    }

    @Test
    void shouldBuyNow() {
        Product product = ProductTestDataBuilder.aProduct().build();
        when(productCatalogService.ofId(product.id())).thenReturn(Optional.of(product));
        when(shippingCostService.calculate(any(ShippingCostService.CalculationRequest.class)))
                .thenReturn(new ShippingCostService.CalculationResult(new Money("10.00"),
                        LocalDate.now().plusDays(3)));

        BuyNowInput input = BuyNowInputTestDataBuilder.aBuyNowInput().build();

        String orderId = buyNowApplicationService.buyNow(input);

        assertThat(orderId).isNotBlank();
        assertThat(orders.exists(new OrderId(orderId))).isTrue();
    }

}