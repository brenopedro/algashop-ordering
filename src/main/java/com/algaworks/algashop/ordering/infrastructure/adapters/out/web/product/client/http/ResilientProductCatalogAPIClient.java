package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.client.http;

import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler.BadGatewayException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler.GatewayTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.core.retry.RetryException;
import org.springframework.resilience.annotation.ConcurrencyLimit;
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
public class ResilientProductCatalogAPIClient {

    private final ProductCatalogAPIClient productCatalogAPIClient;
    private final CircuitBreaker circuitBreaker;

    public ResilientProductCatalogAPIClient(ProductCatalogAPIClient productCatalogAPIClient,
                                            CircuitBreakerFactory circuitBreakerFactory) {
        this.productCatalogAPIClient = productCatalogAPIClient;
        this.circuitBreaker = circuitBreakerFactory.create("productCatalogCB");
    }

    @Cacheable(cacheNames = "algashop:product-catalog-api:v1", key = "#productId")
    @ConcurrencyLimit(10)
    public Optional<ProductResponse> getById(UUID  productId) {

        log.info("Trying to load product {}", productId);
        try {
            return loadProduct(productId);
        } catch (NoFallbackAvailableException ex) {
            throw unwrapException(ex);
        }
    }

    private RuntimeException unwrapException(NoFallbackAvailableException ex) {
        if (ex.getCause() instanceof RetryException re) {
            if (re.getCause() instanceof GatewayTimeoutException gte)
                return gte;
            if (re.getCause() instanceof BadGatewayException bge)
                return bge;
        }
        return ex;
    }

    private Optional<ProductResponse> loadProduct(UUID productId) {
        try {
            log.info("Loading product {}", productId);
            return circuitBreaker.run(() -> Optional.ofNullable(productCatalogAPIClient.getById(productId)));
        } catch (HttpClientErrorException ex) {
            if (!(ex instanceof HttpClientErrorException.NotFound))
                log.error("Client HTTP error when loading product {} ", productId, ex);

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
