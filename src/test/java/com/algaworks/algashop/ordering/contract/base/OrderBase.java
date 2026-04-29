package com.algaworks.algashop.ordering.contract.base;

import com.algaworks.algashop.ordering.application.order.query.OrderDetailOutputTestDataBuilder;
import com.algaworks.algashop.ordering.application.order.query.OrderQueryService;
import com.algaworks.algashop.ordering.domain.model.order.OrderNotFoundException;
import com.algaworks.algashop.ordering.presentation.OrderController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = OrderController.class)
class OrderBase {

    @Autowired
    WebApplicationContext context;

    @MockitoBean
    OrderQueryService orderQueryService;

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

        when(orderQueryService.findById(VALID_ORDER_ID))
                .thenReturn(OrderDetailOutputTestDataBuilder.placedOrder(VALID_ORDER_ID).build());

        when(orderQueryService.findById(NOT_FOUND_ORDER_ID))
                .thenThrow(new OrderNotFoundException());
    }
}

