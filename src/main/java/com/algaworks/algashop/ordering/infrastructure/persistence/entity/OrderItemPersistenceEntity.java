package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_item")
@Entity
@Getter
@Setter
@Builder
@ToString(of = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemPersistenceEntity {

    @Id
    @EqualsAndHashCode.Include
    private Long id;
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;

    @JoinColumn
    @ManyToOne(optional = false)
    private OrderPersistenceEntity order;

    public Long getOrderId() {
        if (getOrderId() == null) {
            return null;
        }
        return getOrder().getId();
    }
}
