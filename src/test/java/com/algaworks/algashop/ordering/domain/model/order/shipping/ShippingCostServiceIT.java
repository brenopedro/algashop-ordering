package com.algaworks.algashop.ordering.domain.model.order.shipping;

import com.algaworks.algashop.ordering.domain.model.AbstractDomainIT;
import com.algaworks.algashop.ordering.domain.model.commons.ZipCode;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService.CalculationRequest;
import static com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService.CalculationResult;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;

class ShippingCostServiceIT extends AbstractDomainIT {

    @Autowired
    private ShippingCostService shippingCostService;

    @Autowired
    private OriginAddressService originAddressService;

    private WireMockServer wireMockRapidex;

    @BeforeEach
    public void setup() {
        initWireMock();
    }

    @AfterEach
    public void clean() {
        wireMockRapidex.stop();
    }

    private void initWireMock() {
        wireMockRapidex = new WireMockServer(options()
                .port(8780)
                .usingFilesUnderDirectory("src/test/resources/wiremock/rapidex"));

        wireMockRapidex.start();
    }


    @Test
    void shouldCalculate() {
        ZipCode origin = originAddressService.originAddress().zipCode();
        ZipCode destination = new ZipCode("12345");

        CalculationResult calculate = shippingCostService
                .calculate(new CalculationRequest(origin, destination));

        assertThat(calculate.cost()).isNotNull();
        assertThat(calculate.expectedDate()).isNotNull();
    }
}