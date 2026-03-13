package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateInput {

    private String firstName;
    private String lastName;
    private String phone;
    private Boolean promotionNotificationsAllowed;
    private AddressData address;
}
