package com.algaworks.algashop.ordering.presentation.customer;

import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.utils.AlgaShopResourceUtils;
import io.restassured.RestAssured;
import io.restassured.path.json.config.JsonPathConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static io.restassured.config.JsonConfig.jsonConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    CustomerPersistenceEntityRepository customerRepository;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        RestAssured.config().jsonConfig(jsonConfig()
                .numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL));
    }

    @Test
    void shouldCreateCustomer() {
        String json = AlgaShopResourceUtils.readContent("json/create-customer.json");
        String createCustomerId = RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                    .body(json)
                .when()
                    .post("/api/v1/customers")
                .then()
                    .assertThat()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .statusCode(HttpStatus.CREATED.value())
                    .body("id", Matchers.not(Matchers.emptyString()))
                    .extract().jsonPath().get("id");

        boolean customerExists = customerRepository.existsById(UUID.fromString(createCustomerId));
        assertThat(customerExists).isTrue();
    }

    @Test
    void shouldNotCreateCustomer() {
        String json = AlgaShopResourceUtils.readContent("json/create-customer-with-invalid-fields.json");
        RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                    .body(json)
                .when()
                    .post("/api/v1/customers")
                .then()
                    .assertThat()
                    .contentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                    .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldArchiveCustomer() {
        CustomerPersistenceEntity customer = CustomerPersistenceEntityTestDataBuilder.aCustomer().build();
        customerRepository.saveAndFlush(customer);

        RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                    .pathParam("customerId", customer.getId().toString())
                .when()
                    .delete("/api/v1/customers/{customerId}")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.NO_CONTENT.value());

        CustomerPersistenceEntity customerEntity = customerRepository.findById(customer.getId()).orElseThrow();
        assertThat(customerEntity).isNotNull();
        assertThat(customerEntity.getArchived()).isTrue();
    }

    @Test
    void shouldThrowExceptionWhenArchiveCustomer() {
        RestAssured
                .given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .contentType("application/json")
                    .pathParam("customerId", UUID.randomUUID().toString())
                .when()
                    .delete("/api/v1/customers/{customerId}")
                .then()
                    .assertThat()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
