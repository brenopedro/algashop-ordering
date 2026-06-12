package com.algaworks.algashop.ordering.infrastructure.adpters.out.persistence.order;

import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.order.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.order.OrderPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.adpters.out.persistence.AbstractPersistenceIT;
import com.algaworks.algashop.ordering.infrastructure.adpters.out.persistence.customer.CustomerPersistenceEntityTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderPersistenceEntityRepositoryIT extends AbstractPersistenceIT {

    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;
    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    private CustomerPersistenceEntity customerPersistenceEntity;

    @Autowired
    public OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderPersistenceEntityRepository, CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @BeforeEach
    void setup() {
        UUID customerID = CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value();
        if (!customerPersistenceEntityRepository.existsById(customerID)) {
            customerPersistenceEntity = customerPersistenceEntityRepository.saveAndFlush(
                    CustomerPersistenceEntityTestDataBuilder.aCustomer().build()
            );
        }
    }

    @Test
    void shouldPersist() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .customer(customerPersistenceEntity)
                .build();

        orderPersistenceEntityRepository.saveAndFlush(entity);
        assertThat(orderPersistenceEntityRepository.existsById(entity.getId())).isTrue();

        OrderPersistenceEntity savedEntity = orderPersistenceEntityRepository.findById(entity.getId()).orElseThrow();

        assertThat(savedEntity.getItems()).isNotEmpty();
    }

    @Test
    void shouldCount() {
        long ordersCount = orderPersistenceEntityRepository.count();
        assertThat(ordersCount).isZero();
    }

    @Test
    void shouldSetAuditingValues() {
        OrderPersistenceEntity entity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .customer(customerPersistenceEntity)
                .build();

        entity = orderPersistenceEntityRepository.saveAndFlush(entity);

        assertThat(entity.getCreatedByUserId()).isNotNull();

        assertThat(entity.getLastModifiedAt()).isNotNull();
        assertThat(entity.getLastModifiedByUserId()).isNotNull();
    }

}