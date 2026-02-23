package com.algaworks.algashop.ordering.domain.model.valueobject;

import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.*;

@Builder
public record Billing(FullName fullName, Document document, Phone phone, Email email, Address address) {

    public Billing {
        Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_INVALID);
        Objects.requireNonNull(document, VALIDATION_ERROR_DOCUMENT_IS_INVALID);
        Objects.requireNonNull(phone, VALIDATION_ERROR_PHONE_IS_INVALID);
        Objects.requireNonNull(email, VALIDATION_ERROR_EMAIL_IS_INVALID);
        Objects.requireNonNull(address, VALIDATION_ERROR_ADDRESS_IS_INVALID);
    }
}
