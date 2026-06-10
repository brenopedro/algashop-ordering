package com.algaworks.algashop.ordering.presentation.order;

import com.algaworks.algashop.ordering.presentation.AbstractPresentationIT;
import com.algaworks.algashop.ordering.utils.AlgaShopResourceUtils;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class OrderControllerWithoutProductCatalogIT extends AbstractPresentationIT {

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
}