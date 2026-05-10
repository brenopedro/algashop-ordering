package com.algaworks.algashop.ordering.infrastructure.product.client.fake;

import com.algaworks.algashop.ordering.domain.model.product.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductCatalogServiceImpl implements ProductCatalogService {

    @Override
    public Optional<Product> ofId(ProductId productId) {
        Product product = Product.builder()
                .id(productId)
                .name(new ProductName("Notebook"))
                .price(new Money("3000"))
                .inStock(true)
                .build();
        return Optional.of(product);
    }
}
