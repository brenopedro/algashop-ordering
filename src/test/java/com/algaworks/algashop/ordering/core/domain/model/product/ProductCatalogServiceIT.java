package com.algaworks.algashop.ordering.core.domain.model.product;

import com.algaworks.algashop.ordering.infrastructure.adapters.out.web.product.client.http.ProductCatalogAPIClient;
import com.algaworks.algashop.ordering.utils.TestContainerPostgreSQLConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;


@Import(TestContainerPostgreSQLConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductCatalogServiceIT {

    @Autowired
    private ProductCatalogService productCatalogService;
    
    @MockitoBean
    private ProductCatalogAPIClient productCatalogAPIClient;
    
    @Test
    void concurrency() {
        UUID rawProductId = UUID.randomUUID();
        ProductId productId = new ProductId(rawProductId);
        when(productCatalogAPIClient.getById(rawProductId)).thenReturn(null);

        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            executorService.submit(() -> productCatalogService.ofId(productId));
            executorService.submit(() -> productCatalogService.ofId(productId));
            executorService.submit(() -> productCatalogService.ofId(productId));
            executorService.submit(() -> productCatalogService.ofId(productId));
            executorService.submit(() -> productCatalogService.ofId(productId));
            executorService.submit(() -> productCatalogService.ofId(productId));
            executorService.awaitTermination(30, TimeUnit.SECONDS);
            executorService.shutdown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}