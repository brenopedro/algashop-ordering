package com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Table(name = "\"shopping_cart_item\"")
@Entity
@Getter
@Setter
@Builder
@ToString(of = "id")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartItemPersistenceEntity {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;
    private String name;
    private UUID productId;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalAmount;
    private Boolean available;

    @JoinColumn(name = "shopping_cart_id")
    @ManyToOne(optional = false)
    private ShoppingCartPersistenceEntity shoppingCart;

    @Version
    private Long version;

    @CreatedBy
    private UUID createdByUserId;

    @CreatedDate
    private OffsetDateTime createdAt;

    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;

    @LastModifiedBy
    private UUID lastModifiedByUserId;

    private ShoppingCartPersistenceEntity getShoppingCart() {
        return shoppingCart;
    }

    public UUID getShoppingCartId() {
        return shoppingCart != null ? shoppingCart.getId() : null;
    }
}
