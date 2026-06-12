package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.excpetionhandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

import com.algaworks.algashop.ordering.core.domain.model.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.DomainException;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerEmailIsInUseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("Invalid fields");
        problemDetail.setDetail("One or more fields are invalid");
        problemDetail.setType(URI.create("/errors/invalid-fields"));

        Map<String, String> fieldErrors = ex.getBindingResult().getAllErrors().stream().collect(
                Collectors.toMap(
                        objectError -> ((FieldError) objectError).getField(),
                        objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale())));

        problemDetail.setProperty("fields", fieldErrors);

        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler(DomainEntityNotFoundException.class)
    public ProblemDetail handleDomainEntityNotFound(DomainEntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Not found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/not-found"));

        return problemDetail;
    }

    @ExceptionHandler({DomainException.class, UnprocessableEntityException.class})
    public ProblemDetail handleUnprocessableEntityException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT);
        problemDetail.setTitle("Unprocessable content");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/unprocessable-content"));

        return problemDetail;
    }

    @ExceptionHandler(CustomerEmailIsInUseException.class)
    public ProblemDetail handleCustomerEmailIsInUseException(CustomerEmailIsInUseException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Conflict");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/conflict"));

        return problemDetail;
    }

    @ExceptionHandler(GatewayTimeoutException.class)
    public ProblemDetail handleGatewayTimeoutException(GatewayTimeoutException ex) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.GATEWAY_TIMEOUT);
        problemDetail.setTitle("Gateway timeout");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/gateway-timeout"));

        return problemDetail;
    }

    @ExceptionHandler(BadGatewayException.class)
    public ProblemDetail handleBadGatewayException(BadGatewayException ex) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_GATEWAY);
        problemDetail.setTitle("Bad gateway");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/bad-gateway"));

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal server error");
        problemDetail.setDetail("An unexpected error occurred.");
        problemDetail.setType(URI.create("/errors/internal"));

        return problemDetail;
    }

}
