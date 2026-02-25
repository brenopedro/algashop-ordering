package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Table(name = "\"order\"")
@Entity
@Getter
@Setter
@Builder
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderPersistenceEntity {

    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private UUID customerId;

    private BigDecimal totalAmount;
    private Integer totalItems;
    private String status;
    private String paymentMethod;

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime canceledAt;
    private OffsetDateTime readyAt;

    @CreatedBy
    private UUID createdByUserId;
    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;
    @LastModifiedBy
    private UUID lastModifiedByUserId;

    @Version
    private Long version;

    @Embedded
    private BillingEmbeddable billing;

    @Embedded
    private ShippingEmbeddable shipping;
}
