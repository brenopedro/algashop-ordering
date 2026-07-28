package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.client.http;

import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler.BadGatewayException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResilientProductCatalogAPIClient {

    private final ProductCatalogAPIClient productCatalogAPIClient;

    @Retryable(
            maxRetries = 3,
            delayString = "3s",
            multiplier = 2,
            includes = {GatewayTimeoutException.class, BadGatewayException.ServerErrorException.class}
    )
    @Cacheable(cacheNames = "algashop:product-catalog-api:v1", key = "#productId")
    @ConcurrencyLimit(10)
    public Optional<ProductResponse> getById(UUID  productId) {

        log.info("Loading product {}", productId);
        try {
            return Optional.ofNullable(productCatalogAPIClient.getById(productId));
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        } catch (RestClientException ex) {
            throw translateException(ex);
        }
    }

    private RuntimeException translateException(RestClientException ex) {
        if (ex.getCause() instanceof SocketTimeoutException || ex instanceof ResourceAccessException)
            return new GatewayTimeoutException("Product Catalog API Timeout", ex);

        if (ex instanceof HttpClientErrorException)
            return new BadGatewayException.ClientErrorException("Product Catalog API Client Error", ex);

        if (ex instanceof HttpServerErrorException)
            return new BadGatewayException.ServerErrorException("Product Catalog API Server Error", ex);

        return new BadGatewayException("Product Catalog API Bad Gateway", ex);
    }
}
