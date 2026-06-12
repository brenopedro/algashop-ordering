package com.algaworks.algashop.ordering.contract.base;

import com.algaworks.algashop.ordering.core.application.checkout.BuyNowApplicationService;
import com.algaworks.algashop.ordering.core.application.checkout.CheckoutApplicationService;
import com.algaworks.algashop.ordering.core.application.order.OrderDetailOutputTestDataBuilder;
import com.algaworks.algashop.ordering.core.application.order.OrderSummaryOutputTestDataBuilder;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderNotFoundException;
import com.algaworks.algashop.ordering.core.ports.in.checkout.BuyNowInput;
import com.algaworks.algashop.ordering.core.ports.in.checkout.CheckoutInput;
import com.algaworks.algashop.ordering.core.ports.in.order.OrderFilter;
import com.algaworks.algashop.ordering.core.ports.out.order.ForObtainingOrders;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.order.OrderController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = OrderController.class)
class OrderBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ForObtainingOrders orderQueryService;

    @MockitoBean
    private CheckoutApplicationService checkoutApplicationService;

    @MockitoBean
    private BuyNowApplicationService buyNowApplicationService;

    public static final String VALID_ORDER_ID = "01226N0640J7Q";
    public static final String NOT_FOUND_ORDER_ID = "01226N0693HDH";

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(context)
                        .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                        .build()
        );
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        mockValidOrderId();
        mockInvalidOrderId();
        mockFilter();
        mockBuyNow();
        mockCheckout();
    }

    private void mockValidOrderId() {
        when(orderQueryService.findById(VALID_ORDER_ID))
                .thenReturn(OrderDetailOutputTestDataBuilder.placedOrder(VALID_ORDER_ID).build());
    }

    private void mockInvalidOrderId() {
        when(orderQueryService.findById(NOT_FOUND_ORDER_ID))
                .thenThrow(new OrderNotFoundException());
    }

    private void mockFilter() {
        when(orderQueryService.filter(any(OrderFilter.class)))
                .thenReturn(new PageImpl<>(List.of(OrderSummaryOutputTestDataBuilder
                        .placedOrder().id(VALID_ORDER_ID).build())));
    }

    private void mockBuyNow() {
        when(buyNowApplicationService.buyNow(any(BuyNowInput.class)))
                .thenReturn(VALID_ORDER_ID);
    }

    private void mockCheckout() {
        when(checkoutApplicationService.checkout(any(CheckoutInput.class)))
                .thenReturn(VALID_ORDER_ID);
    }
}

