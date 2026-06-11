package com.algaworks.algashop.ordering.core.domain.model.commons;

import java.util.Objects;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_DOCUMENT_IS_INVALID;

public record Document(String value) {

    public Document {
        Objects.requireNonNull(value, VALIDATION_ERROR_DOCUMENT_IS_INVALID);

        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_DOCUMENT_IS_INVALID);
        }
    }

    @Override
    public String toString() {
        return value();
    }
}
