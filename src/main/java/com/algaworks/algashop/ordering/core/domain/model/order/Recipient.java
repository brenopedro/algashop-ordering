package com.algaworks.algashop.ordering.core.domain.model.order;

import com.algaworks.algashop.ordering.core.domain.model.commons.Document;
import com.algaworks.algashop.ordering.core.domain.model.commons.FullName;
import com.algaworks.algashop.ordering.core.domain.model.commons.Phone;
import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.core.domain.model.ErrorMessages.*;

@Builder
public record Recipient(FullName fullName, Document document, Phone phone) {

        public Recipient {
            Objects.requireNonNull(fullName, VALIDATION_ERROR_FULLNAME_IS_INVALID);
            Objects.requireNonNull(document, VALIDATION_ERROR_DOCUMENT_IS_INVALID);
            Objects.requireNonNull(phone, VALIDATION_ERROR_PHONE_IS_INVALID);
        }
}
