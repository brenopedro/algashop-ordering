package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;

public class CustomerUpdateInputTestDateBuilder {

    public static CustomerUpdateInput.CustomerUpdateInputBuilder builder() {
        return CustomerUpdateInput.builder()
                .firstName("Matt")
                .lastName("Damon")
                .phone("123-321-1112")
                .promotionNotificationsAllowed(true)
                .address(AddressData.builder()
                        .street("Amphitheatre Parkway")
                        .number("1600")
                        .complement("Apt. 405")
                        .neighborhood("Mountain View")
                        .city("Mountain View")
                        .state("California")
                        .zipCode("43212")
                        .build());
    }
}
