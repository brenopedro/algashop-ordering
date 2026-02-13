package com.algaworks.algashop.ordering.domain.exception;

public class ErrorMessages {

    private ErrorMessages() {}

    public static final String VALIDATION_ERROR_CUSTOMER_ID_IS_INVALID = "CustomerId is invalid";
    public static final String VALIDATION_ERROR_ORDER_ID_IS_INVALID = "OrderId is invalid";
    public static final String VALIDATION_ERROR_ORDER_ITEM_ID_IS_INVALID = "OrderItemId is invalid";
    public static final String VALIDATION_ERROR_PRODUCT_ID_IS_INVALID = "ProductId is invalid";

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
    public static final String VALIDATION_ERROR_ADDRESS_IS_INVALID = "Address is invalid";

    public static final String VALIDATION_ERROR_MONEY_IS_INVALID = "Money is invalid";

    public static final String VALIDATION_ERROR_QUANTITY_IS_INVALID = "Quantity is invalid";

    public static final String VALIDATION_ERROR_PRODUCT_NAME_IS_INVALID = "Product name is invalid";

    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is already archived";

    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Order %s cannot change status from %s to %s";

    public static final String ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST = "Order %s cannot have delivery date in the past";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS = "Order %s cannot be placed, it has no items";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO = "Order %s cannot be placed, it has no shipping info";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO = "Order %s cannot be placed, it has no billing info";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_EXPECTED_DELIVERY_DATE = "Order %s placed be placed, it has no expected delivery date";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_COST = "Order %s cannot be placed, it has no shipping cost";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD = "Order %s cannot be placed, it has no payment method";

    public static final String ERROR_ORDER_DOES_NOT_CONTAIN_ORDER_ITEM = "Order %s does not contain order item %s";

}
