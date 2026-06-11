package com.algaworks.algashop.ordering.core.domain.model.product;

import com.algaworks.algashop.ordering.core.domain.model.FieldValidator;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_PRODUCT_NAME_IS_INVALID;

public record ProductName(String value) {

     public ProductName {
         FieldValidator.requiresNonBlank(value, VALIDATION_ERROR_PRODUCT_NAME_IS_INVALID);
     }

     @Override
     public String toString() {
         return value();
     }
}
