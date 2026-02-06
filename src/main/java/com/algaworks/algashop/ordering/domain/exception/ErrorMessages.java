package com.algaworks.algashop.ordering.domain.exception;

public class ErrorMessages {

    private ErrorMessages() {}

    public static final String VALIDATION_ERROR_CUSTOMER_ID_IS_INVALID = "CustomerId is invalid";

    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";
    public static final String VALIDATION_ERROR_BIRTHDATE_IS_INVALID = "BirthDate is invalid";

    public static final String VALIDATION_ERROR_FULLNAME_IS_INVALID = "FullName is invalid";
    public static final String VALIDATION_ERROR_FIRSTNAME_IS_INVALID = "Firstname is invalid";
    public static final String VALIDATION_ERROR_LASTNAME_IS_INVALID = "Lastname is invalid";

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";

    public static final String VALIDATION_ERROR_DOCUMENT_IS_INVALID = "Document is invalid";

    public static final String VALIDATION_ERROR_PHONE_IS_INVALID = "Phone is invalid";

    public static final String VALIDATION_ERROR_LOYALTY_POINTS_IS_INVALID = "Loyalty points is invalid";
    public static final String VALIDATION_ERROR_LOYALTY_POINTS_MUST_BE_POSITIVE = "Loyalty points must be a positive number";

    public static final String VALIDATION_ERROR_ZIP_CODE_IS_INVALID = "ZipCode is invalid";
    public static final String VALIDATION_ERROR_ZIP_CODE_WRONG_SIZE = "ZipCode must be 5 characters long";

    public static final String VALIDATION_ERROR_STREET_IS_INVALID = "Street is invalid";
    public static final String VALIDATION_ERROR_NUMBER_IS_INVALID = "Number is invalid";
    public static final String VALIDATION_ERROR_NEIGHBORHOOD_IS_INVALID = "Neighborhood is invalid";
    public static final String VALIDATION_ERROR_CITY_IS_INVALID = "City is invalid";
    public static final String VALIDATION_ERROR_STATE_IS_INVALID = "State is invalid";


    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is already archived";
}
