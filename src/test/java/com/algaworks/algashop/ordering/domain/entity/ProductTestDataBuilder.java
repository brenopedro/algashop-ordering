package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.Product;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;

public class ProductTestDataBuilder {

    private ProductTestDataBuilder() {}

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(new ProductId())
                .inStock(true)
                .name(new ProductName("Desktop"))
                .price(new Money("3000"));
    }

    public static Product.ProductBuilder aProductUnavailable() {
        return Product.builder()
                .id(new ProductId())
                .inStock(false)
                .name(new ProductName("Notebook"))
                .price(new Money("5000"));
    }

    public static Product.ProductBuilder aProductAltRamMemory() {
        return Product.builder()
                .id(new ProductId())
                .inStock(false)
                .name(new ProductName("4GB RAM"))
                .price(new Money("200"));
    }
}
