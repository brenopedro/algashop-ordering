package com.algaworks.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

@Builder
public record ShippingInfo(FullName fullName, Document document, Phone phone, Address address) {

    public ShippingInfo {
        Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_INVALID);
        Objects.requireNonNull(document, VALIDATION_ERROR_DOCUMENT_IS_INVALID);
        Objects.requireNonNull(phone, VALIDATION_ERROR_PHONE_IS_INVALID);
        Objects.requireNonNull(address, VALIDATION_ERROR_ADDRESS_IS_INVALID);
    }
}
