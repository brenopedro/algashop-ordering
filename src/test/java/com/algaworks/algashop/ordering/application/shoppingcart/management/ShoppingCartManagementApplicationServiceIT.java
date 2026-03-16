package com.algaworks.algashop.ordering.application.shoppingcart.management;

import com.algaworks.algashop.ordering.domain.model.commons.Quantity;
import com.algaworks.algashop.ordering.domain.model.customer.*;
import com.algaworks.algashop.ordering.domain.model.product.*;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
class ShoppingCartManagementApplicationServiceIT {

    @Autowired
    private ShoppingCartManagementApplicationService service;

    @Autowired
    private ShoppingCarts shoppingCarts;

    @Autowired
    private Customers customers;

    @MockitoBean
    private ProductCatalogService productCatalogService;

    @Test
    void shouldCreateNewShoppingCartForExistingCustomer() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);

        UUID newShoppingCartId = service.createNew(customer.id().value());

        assertThat(newShoppingCartId).isNotNull();
        Optional<ShoppingCart> createdCart = shoppingCarts.ofId(new com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId(newShoppingCartId));
        assertThat(createdCart).isPresent();
        assertThat(createdCart.get().customerId().value()).isEqualTo(customer.id().value());
        assertThat(createdCart.get().isEmpty()).isTrue();
    }

    @Test
    void shouldThrowCustomerNotFoundExceptionWhenCreatingNewShoppingCartForNonExistingCustomer() {
        UUID nonExistingCustomerId = UUID.randomUUID();

        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> service.createNew(nonExistingCustomerId));
    }

    @Test
    void shouldThrowCustomerAlreadyHaveShoppingCartExceptionWhenCreatingNewShoppingCartForCustomerWithExistingCart() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        ShoppingCart existingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(existingCart);

        assertThatExceptionOfType(CustomerAlreadyHaveShoppingCartException.class)
                .isThrownBy(() -> service.createNew(customer.id().value()));
    }

    @Test
    void shouldAddItemToShoppingCartSuccessfully() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(shoppingCart);

        Product product = ProductTestDataBuilder.aProduct().inStock(true).build();
        Mockito.when(productCatalogService.ofId(product.id())).thenReturn(Optional.of(product));

        ShoppingCartItemInput input = ShoppingCartItemInput.builder()
                .shoppingCartId(shoppingCart.id().value())
                .productId(product.id().value())
                .quantity(2)
                .build();

        service.addItem(input);

        ShoppingCart updatedCart = shoppingCarts.ofId(shoppingCart.id()).orElseThrow();
        assertThat(updatedCart.items()).hasSize(1);
        assertThat(updatedCart.items().iterator().next().productId()).isEqualTo(product.id());
        assertThat(updatedCart.items().iterator().next().quantity().value()).isEqualTo(2);
    }

    @Test
    void shouldThrowShoppingCartNotFoundExceptionWhenAddingItemToNonExistingShoppingCart() {
        UUID nonExistingCartId = UUID.randomUUID();
        Product product = ProductTestDataBuilder.aProduct().inStock(true).build();
        Mockito.when(productCatalogService.ofId(product.id())).thenReturn(Optional.of(product));

        ShoppingCartItemInput input = ShoppingCartItemInput.builder()
                .shoppingCartId(nonExistingCartId)
                .productId(product.id().value())
                .quantity(1)
                .build();

        assertThatExceptionOfType(ShoppingCartNotFoundException.class)
                .isThrownBy(() -> service.addItem(input));
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenAddingNonExistingProduct() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(shoppingCart);

        Mockito.when(productCatalogService.ofId(Mockito.any())).thenReturn(Optional.empty());

        ShoppingCartItemInput input = ShoppingCartItemInput.builder()
                .shoppingCartId(shoppingCart.id().value())
                .productId(UUID.randomUUID())
                .quantity(1)
                .build();

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> service.addItem(input));
    }

    @Test
    void shouldThrowProductOutOfStockExceptionWhenAddingOutOfStockProduct() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(shoppingCart);

        Product outOfStockProduct = ProductTestDataBuilder.aProduct().inStock(false).build();
        Mockito.when(productCatalogService.ofId(outOfStockProduct.id())).thenReturn(Optional.of(outOfStockProduct));

        ShoppingCartItemInput input = ShoppingCartItemInput.builder()
                .shoppingCartId(shoppingCart.id().value())
                .productId(outOfStockProduct.id().value())
                .quantity(1)
                .build();

        assertThatExceptionOfType(ProductOutOfStockException.class)
                .isThrownBy(() -> service.addItem(input));
    }

    @Test
    void shouldRemoveItemFromShoppingCartSuccessfully() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        Product product = ProductTestDataBuilder.aProduct().inStock(true).build();
        shoppingCart.addItem(product, new Quantity(1));
        shoppingCarts.add(shoppingCart);

        ShoppingCartItem itemToRemove = shoppingCart.items().iterator().next();

        service.removeItem(shoppingCart.id().value(), itemToRemove.id().value());

        ShoppingCart updatedCart = shoppingCarts.ofId(shoppingCart.id()).orElseThrow();
        assertThat(updatedCart.items()).isEmpty();
    }

    @Test
    void shouldThrowShoppingCartNotFoundExceptionWhenRemovingItemFromNonExistingShoppingCart() {
        UUID nonExistingCartId = UUID.randomUUID();
        UUID dummyItemId = UUID.randomUUID();

        assertThatExceptionOfType(ShoppingCartNotFoundException.class)
                .isThrownBy(() -> service.removeItem(nonExistingCartId, dummyItemId));
    }

    @Test
    void shouldThrowShoppingCartDoesNotContainItemExceptionWhenRemovingNonExistingItem() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(shoppingCart);

        UUID nonExistingItemId = UUID.randomUUID();

        assertThatExceptionOfType(ShoppingCartDoesNotContainItemException.class)
                .isThrownBy(() -> service.removeItem(shoppingCart.id().value(), nonExistingItemId));
    }

    @Test
    void shouldEmptyShoppingCartSuccessfully() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCart.addItem(ProductTestDataBuilder.aProduct().inStock(true).build(), new Quantity(1));
        shoppingCarts.add(shoppingCart);

        service.empty(shoppingCart.id().value());

        ShoppingCart updatedCart = shoppingCarts.ofId(shoppingCart.id()).orElseThrow();
        assertThat(updatedCart.isEmpty()).isTrue();
    }

    @Test
    void shouldThrowShoppingCartNotFoundExceptionWhenEmptyingNonExistingShoppingCart() {
        UUID nonExistingCartId = UUID.randomUUID();

        assertThatExceptionOfType(ShoppingCartNotFoundException.class)
                .isThrownBy(() -> service.empty(nonExistingCartId));
    }

    @Test
    void shouldDeleteShoppingCartSuccessfully() {
        Customer customer = CustomerTestDataBuilder.brandNewCustomer().build();
        customers.add(customer);
        ShoppingCart shoppingCart = ShoppingCart.startShopping(customer.id());
        shoppingCarts.add(shoppingCart);

        service.delete(shoppingCart.id().value());

        Optional<ShoppingCart> deletedCart = shoppingCarts.ofId(shoppingCart.id());
        assertThat(deletedCart).isNotPresent();
    }

    @Test
    void shouldThrowShoppingCartNotFoundExceptionWhenDeletingNonExistingShoppingCart() {
        UUID nonExistingCartId = UUID.randomUUID();

        assertThatExceptionOfType(ShoppingCartNotFoundException.class)
                .isThrownBy(() -> service.delete(nonExistingCartId));
    }
}