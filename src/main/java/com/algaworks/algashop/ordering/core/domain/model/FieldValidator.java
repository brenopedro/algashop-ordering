package com.algaworks.algashop.ordering.core.domain.model;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class FieldValidator {

    private  FieldValidator() {
    }

    public static void requiresNonBlank(String value) {
        requiresNonBlank(value, "");
    }

    public static void requiresNonBlank(String value, String errorMessage) {
        Objects.requireNonNull(value, errorMessage);
        if (value.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void requiresValidEmail(String email) {
        requiresValidEmail(email, null);
    }

    public static void requiresValidEmail(String email, String errorMessage) {
        requiresNonBlank(email, errorMessage);
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
