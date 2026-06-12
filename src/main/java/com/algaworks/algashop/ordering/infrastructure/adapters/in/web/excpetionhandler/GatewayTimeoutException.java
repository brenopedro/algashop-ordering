package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler;

public class GatewayTimeoutException extends RuntimeException {
    public GatewayTimeoutException() {

    }

    public GatewayTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
