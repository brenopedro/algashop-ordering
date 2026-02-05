package com.algaworks.algashop.ordering.domain.excpetion;

public class ErrorMessages {

    private ErrorMessages() {}

    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";

    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null";
    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank";
    public static final String VALIDATION_ERROR_FIRSTNAME_IS_NULL = "Firstname cannot be null";
    public static final String VALIDATION_ERROR_FIRSTNAME_IS_BLANK = "Firstname cannot be blank";
    public static final String VALIDATION_ERROR_LASTNAME_IS_NULL = "Lastname cannot be null";
    public static final String VALIDATION_ERROR_LASTNAME_IS_BLANK = "Lastname cannot be blank";

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";

    public static final String VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE = "Loyalty points must be a positive number";

    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is already archived";
}
