package com.algaworks.algashop.ordering.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractEventSourceEntity implements DomainEventSource {

    protected final List<Object> domainEvents = new ArrayList<>();

    protected void publishDomainEvent(Object domainEvent) {
        Objects.requireNonNull(domainEvent);
        this.domainEvents.add(domainEvent);
    }

    @Override
    public List<Object> domainEvents() {
        return Collections.unmodifiableList(this.domainEvents);
    }

    @Override
    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
}
