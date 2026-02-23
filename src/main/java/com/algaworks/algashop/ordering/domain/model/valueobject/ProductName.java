package com.algaworks.algashop.ordering.domain.model.valueobject;

import com.algaworks.algashop.ordering.domain.model.validator.FieldValidator;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_PRODUCT_NAME_IS_INVALID;

public record ProductName(String value) {

     public ProductName {
         FieldValidator.requiresNonBlank(value, VALIDATION_ERROR_PRODUCT_NAME_IS_INVALID);
     }

     @Override
     public String toString() {
         return value();
     }
}
