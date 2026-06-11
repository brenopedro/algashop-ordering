package com.algaworks.algashop.ordering.core.domain.model.customer;

import com.algaworks.algashop.ordering.core.domain.model.DomainException;
import com.algaworks.algashop.ordering.core.domain.model.commons.Email;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.VALIDATION_ERROR_EMAIL_IN_USE;

public class CustomerEmailIsInUseException extends DomainException {
    public CustomerEmailIsInUseException(Email email) {
        super(String.format(VALIDATION_ERROR_EMAIL_IN_USE, email.value()));
    }
}
