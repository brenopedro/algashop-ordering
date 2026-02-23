package com.algaworks.algashop.ordering.domain.model.valueobject;

import com.algaworks.algashop.ordering.domain.model.validator.FieldValidator;
import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.*;

@Builder(toBuilder = true)
public record Address(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        ZipCode zipCode
) {

    public Address {
        FieldValidator.requiresNonBlank(street, VALIDATION_ERROR_STREET_IS_INVALID);
        FieldValidator.requiresNonBlank(number, VALIDATION_ERROR_NUMBER_IS_INVALID);
        FieldValidator.requiresNonBlank(neighborhood, VALIDATION_ERROR_NEIGHBORHOOD_IS_INVALID);
        FieldValidator.requiresNonBlank(city, VALIDATION_ERROR_CITY_IS_INVALID);
        FieldValidator.requiresNonBlank(state, VALIDATION_ERROR_STATE_IS_INVALID);
        Objects.requireNonNull(zipCode, VALIDATION_ERROR_ZIP_CODE_IS_INVALID);
    }
}
