package com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.order;

import com.algaworks.algashop.ordering.infrastructure.adapters.out.commons.AddressEmbeddable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BillingEmbeddable {

    private String firstName;
    private String lastName;
    private String document;
    private String phone;
    private String email;

    @Embedded
    private AddressEmbeddable address;
}
