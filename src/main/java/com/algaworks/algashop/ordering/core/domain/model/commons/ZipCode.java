package com.algaworks.algashop.ordering.core.domain.model.commons;

import com.algaworks.algashop.ordering.core.domain.model.FieldValidator;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_ZIP_CODE_IS_INVALID;
import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_ZIP_CODE_WRONG_SIZE;

public record ZipCode(String value) {

     public ZipCode {
         FieldValidator.requiresNonBlank(value, VALIDATION_ERROR_ZIP_CODE_IS_INVALID);

         if (value.length() != 5) {
             throw new IllegalArgumentException(VALIDATION_ERROR_ZIP_CODE_WRONG_SIZE);
         }
     }

     @Override
     public String toString() {
         return value();
     }
}
