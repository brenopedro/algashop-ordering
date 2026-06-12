package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.shoppingcart;

import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductNotFoundException;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForManagingShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartItemInput;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartOutput;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler.UnprocessableEntityException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ForQueryingShoppingCarts queryService;
    private final ForManagingShoppingCarts forManagingShoppingCarts;

    @GetMapping("/{shoppingCartId}")
    public ShoppingCartOutput getById(@PathVariable UUID shoppingCartId) {
        return queryService.findById(shoppingCartId);
    }

    @GetMapping("/{shoppingCartId}/items")
    public ShoppingCartItemListModel getItems(@PathVariable UUID shoppingCartId) {
        return new ShoppingCartItemListModel(queryService.findById(shoppingCartId).getItems());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartOutput create(@RequestBody @Valid ShoppingCartInput input) {
        UUID id;
        try {
            id = forManagingShoppingCarts.createNew(input.getCustomerId());
        } catch (CustomerNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
        return queryService.findById(id);
    }

    @PostMapping("/{shoppingCartId}/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItem(@RequestBody @Valid ShoppingCartItemInput input, @PathVariable UUID shoppingCartId) {
        input.setShoppingCartId(shoppingCartId);
        try {
            forManagingShoppingCarts.addItem(input);
        } catch (ProductNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{shoppingCartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID shoppingCartId) {
        forManagingShoppingCarts.delete(shoppingCartId);
    }

    @DeleteMapping("/{shoppingCartId}/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void empty(@PathVariable UUID shoppingCartId) {
        forManagingShoppingCarts.empty(shoppingCartId);
    }

    @DeleteMapping("/{shoppingCartId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable UUID shoppingCartId, @PathVariable UUID itemId) {
        forManagingShoppingCarts.removeItem(shoppingCartId, itemId);
    }
}
