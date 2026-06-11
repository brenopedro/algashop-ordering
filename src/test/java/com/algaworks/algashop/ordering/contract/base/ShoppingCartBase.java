package com.algaworks.algashop.ordering.contract.base;

import com.algaworks.algashop.ordering.core.application.shoppingcart.ShoppingCartManagementApplicationService;
import com.algaworks.algashop.ordering.core.application.shoppingcart.query.ShoppingCartOutputTestDataBuilder;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.shoppingcart.ShoppingCartController;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.UUID;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ShoppingCartController.class)
class ShoppingCartBase {

    @Autowired
    WebApplicationContext context;

    @MockitoBean
    ForQueryingShoppingCarts queryService;

    @MockitoBean
    ShoppingCartManagementApplicationService managementApplicationService;

    public static final UUID VALID_SHOPPING_CART_ID = UUID.fromString("ad265aa3-c77d-46e9-9782-b70c487c1e17");
    public static final UUID NOT_FOUND_SHOPPING_CART_ID = UUID.fromString("e2103964-5353-4910-81ee-212a40a2ca70");

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(context)
                        .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                        .build()
        );
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        when(queryService.findById(VALID_SHOPPING_CART_ID))
                .thenReturn(ShoppingCartOutputTestDataBuilder.aShoppingCart().id(VALID_SHOPPING_CART_ID).build());

        when(queryService.findById(NOT_FOUND_SHOPPING_CART_ID))
                .thenThrow(new ShoppingCartNotFoundException());

        when(managementApplicationService.createNew(any(UUID.class)))
                .thenReturn(VALID_SHOPPING_CART_ID);
    }
}
