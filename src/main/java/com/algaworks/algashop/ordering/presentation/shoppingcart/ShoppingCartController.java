package com.algaworks.algashop.ordering.presentation.shoppingcart;

import com.algaworks.algashop.ordering.application.shoppingcart.management.ShoppingCartItemInput;
import com.algaworks.algashop.ordering.application.shoppingcart.management.ShoppingCartManagementApplicationService;
import com.algaworks.algashop.ordering.application.shoppingcart.query.ShoppingCartOutput;
import com.algaworks.algashop.ordering.application.shoppingcart.query.ShoppingCartQueryService;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import com.algaworks.algashop.ordering.domain.model.product.ProductNotFoundException;
import com.algaworks.algashop.ordering.presentation.UnprocessableEntityException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-carts")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartQueryService queryService;
    private final ShoppingCartManagementApplicationService managementApplication;

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
            id = managementApplication.createNew(input.getCustomerId());
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
            managementApplication.addItem(input);
        } catch (ProductNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{shoppingCartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID shoppingCartId) {
        managementApplication.delete(shoppingCartId);
    }

    @DeleteMapping("/{shoppingCartId}/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void empty(@PathVariable UUID shoppingCartId) {
        managementApplication.empty(shoppingCartId);
    }

    @DeleteMapping("/{shoppingCartId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable UUID shoppingCartId, @PathVariable UUID itemId) {
        managementApplication.removeItem(shoppingCartId, itemId);
    }
}
