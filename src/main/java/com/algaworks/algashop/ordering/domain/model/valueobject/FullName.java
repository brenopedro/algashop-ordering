package com.algaworks.algashop.ordering.domain.model.valueobject;

import com.algaworks.algashop.ordering.domain.model.validator.FieldValidator;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.*;

public record FullName(String firstName, String lastName) {

     public FullName(String firstName, String lastName) {
         FieldValidator.requiresNonBlank(firstName, VALIDATION_ERROR_FIRSTNAME_IS_INVALID);
         FieldValidator.requiresNonBlank(lastName, VALIDATION_ERROR_LASTNAME_IS_INVALID);

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
     }

     @Override
     public String toString() {
          return firstName + " " + lastName;
     }
}
