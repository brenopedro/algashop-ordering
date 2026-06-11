package com.algaworks.algashop.ordering.presentation.order;

import com.algaworks.algashop.ordering.core.application.checkout.BuyNowInput;
import com.algaworks.algashop.ordering.core.application.checkout.BuyNowInputTestDataBuilder;
import com.algaworks.algashop.ordering.core.application.order.query.OrderDetailOutput;
import com.algaworks.algashop.ordering.core.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart.ShoppingCartPersistenceEntityRepository;
import com.algaworks.algashop.ordering.presentation.AbstractPresentationIT;
import com.algaworks.algashop.ordering.utils.AlgaShopResourceUtils;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

class OrderControllerIT extends AbstractPresentationIT {


    @Autowired
    private CustomerPersistenceEntityRepository customerRepository;

    @Autowired
    private OrderPersistenceEntityRepository orderRepository;

    @Autowired
    private ShoppingCartPersistenceEntityRepository shoppingCartRepository;

    private static final UUID validCustomerId = UUID.fromString("6e148bd5-47f6-4022-b9da-07cfaa294f7a");
    private static final UUID validProductId = UUID.fromString("fffee8b1-9c3a-4c5b-8f1e-2d9a7b6c8e9f");

    @BeforeAll
    static void beforeAll() {
        AbstractPresentationIT.initWireMock();
    }

    @BeforeEach
    void setup() {
        super.beforeEach();
    }

    @AfterAll
    static void afterAll() {
        AbstractPresentationIT.stopMock();
    }

    @Test
    void shouldCreateOrderUsingProduct() {
        String json = AlgaShopResourceUtils.readContent("json/create-order-with-product.json");

        String createdOrderId = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.not(Matchers.emptyString()),
                        "customer.id", Matchers.is(validCustomerId.toString()))
                .extract()
                .jsonPath().getString("id");

        boolean orderExists = orderRepository.existsById(new OrderId(createdOrderId).value().toLong());
        Assertions.assertThat(orderExists).isTrue();

    }

    @Test
    void shouldCreateOrderUsingProduct_DTO() {
        UUID creditCardId = UUID.randomUUID();
        BuyNowInput input = BuyNowInputTestDataBuilder.aBuyNowInput()
                .productId(validProductId)
                .customerId(validCustomerId)
                .creditCardId(creditCardId)
                .build();

        OrderDetailOutput orderDetailOutput = RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(input)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .statusCode(HttpStatus.CREATED.value())
                .body("id", Matchers.not(Matchers.emptyString()),
                        "customer.id", Matchers.is(validCustomerId.toString()))
                .extract()
                .body().as(OrderDetailOutput.class);

        Assertions.assertThat(orderDetailOutput.getCustomer().getId()).isEqualTo(validCustomerId);
        Assertions.assertThat(orderDetailOutput.getCreditCardId()).isEqualTo(creditCardId);

        boolean orderExists = orderRepository.existsById(new OrderId(orderDetailOutput.getId()).value().toLong());
        Assertions.assertThat(orderExists).isTrue();
    }

    @Test
    @Disabled
    void shouldNotCreateOrderUsingProductWhenProductAPIIsUnavailable() {
        String json = AlgaShopResourceUtils.readContent("json/create-order-with-product.json");

        wireMockProductCatalog.stop();

        RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .statusCode(HttpStatus.GATEWAY_TIMEOUT.value());

    }

    @Test
    void shouldNotCreateOrderUsingProductWhenProductNotExists() {
        String json = AlgaShopResourceUtils.readContent("json/create-order-with-invalid-product.json");

        RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());

    }

    @Test
    void shouldNotCreateOrderUsingProductWhenCustomerWasNotFound() {
        String json = AlgaShopResourceUtils.readContent("json/create-order-with-product-and-invalid-customer.json");
        RestAssured
                .given()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType("application/vnd.order-with-product.v1+json")
                .body(json)
                .when()
                .post("/api/v1/orders")
                .then()
                .assertThat()
                .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}