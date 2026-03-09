package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.Email;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_EMAIL_IN_USE;

public class CustomerEmailIsInUseException extends DomainException {
    public CustomerEmailIsInUseException(Email email) {
        super(String.format(VALIDATION_ERROR_EMAIL_IN_USE, email.value()));
    }
}
