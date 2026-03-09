package com.algaworks.algashop.ordering.domain.model.exception;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.ERROR_ORDER_IS_NOT_READY_TO_ADD_LOYALTY_POINTS;

public class CantAddLoyaltyPointsOrderIsNotReadyException extends DomainException {

    public CantAddLoyaltyPointsOrderIsNotReadyException() {
        super(ERROR_ORDER_IS_NOT_READY_TO_ADD_LOYALTY_POINTS);
    }
}
