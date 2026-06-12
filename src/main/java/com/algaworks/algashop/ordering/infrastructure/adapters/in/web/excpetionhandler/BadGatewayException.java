package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler;

public class BadGatewayException extends RuntimeException {
    public BadGatewayException() {

    }

    public BadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
