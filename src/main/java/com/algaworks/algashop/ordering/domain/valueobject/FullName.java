package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public record FullName(String firstName, String lastName) {

     public FullName(String firstName, String lastName) {
          Objects.requireNonNull(firstName, VALIDATION_ERROR_FIRSTNAME_IS_NULL);
          Objects.requireNonNull(lastName, VALIDATION_ERROR_LASTNAME_IS_NULL);

            if (firstName.isBlank()) {
                 throw new IllegalArgumentException(VALIDATION_ERROR_FIRSTNAME_IS_BLANK);
            }
            if (lastName.isBlank()) {
                 throw new IllegalArgumentException(VALIDATION_ERROR_LASTNAME_IS_BLANK);
            }

            this.firstName = firstName.trim();
            this.lastName = lastName.trim();
     }

     @Override
     public String toString() {
          return firstName + " " + lastName;
     }
}
