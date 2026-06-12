package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException() {
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    public UnprocessableEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
