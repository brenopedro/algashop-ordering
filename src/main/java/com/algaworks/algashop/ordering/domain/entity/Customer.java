package com.algaworks.algashop.ordering.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter(onMethod_ = {@Accessors(fluent = true)})
@Setter(AccessLevel.PRIVATE)
public class Customer {

    private UUID id;
    private String fullName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private Integer loyaltyPoints;


    public Customer(UUID id, String fullName, LocalDate birthDate, String email, String phone, String document,
                    Boolean promotionNotificationsAllowed, Boolean archived, OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt, Integer loyaltyPoints) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.document = document;
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
        this.archived = archived;
        this.registeredAt = registeredAt;
        this.archivedAt = archivedAt;
        this.loyaltyPoints = loyaltyPoints;
    }

    public Customer(UUID id, String fullName, LocalDate birthDate, String email, String phone, String document,
                    Boolean promotionNotificationsAllowed, OffsetDateTime registeredAt) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.document = document;
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
        this.registeredAt = registeredAt;
    }

    public void addLoyaltyPoints(Integer points) {
        if (this.loyaltyPoints == null) {
            this.loyaltyPoints = 0;
        }
        this.loyaltyPoints += points;
    }

    public void archive() {
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
    }

    public void enablePromotionNotifications() {
        this.setPromotionNotificationsAllowed(true);
    }

    public void disablePromotionNotifications() {
        this.setPromotionNotificationsAllowed(false);
    }

    public void changeName(String fullName) {
        this.setFullName(fullName);
    }

    public void changeEmail(String email) {
        this.setEmail(email);
    }

    public void changePhone(String phone) {
        this.setPhone(phone);
    }

    private void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
