package com.algaworks.algashop.ordering.core.domain.model.commons;

import com.algaworks.algashop.ordering.core.domain.model.FieldValidator;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_PHONE_IS_INVALID;

public record Phone(String value) {

    public Phone {
        FieldValidator.requiresNonBlank(value, VALIDATION_ERROR_PHONE_IS_INVALID);
    }

    @Override
    public String toString() {
        return value();
    }
}
