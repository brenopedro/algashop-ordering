package com.algaworks.algashop.ordering.core.domain.model;

public class ErrorMessages {

    private ErrorMessages() {}

    public static final String VALIDATION_ERROR_CUSTOMER_IS_NULL = "Customer must not be null";
    public static final String VALIDATION_ERROR_ORDER_IS_NULL = "Order must not be null";

    public static final String VALIDATION_ERROR_CUSTOMER_ID_IS_INVALID = "CustomerId is invalid";
    public static final String VALIDATION_ERROR_ORDER_ID_IS_INVALID = "OrderId is invalid";
    public static final String VALIDATION_ERROR_ORDER_ITEM_ID_IS_INVALID = "OrderItemId is invalid";
    public static final String VALIDATION_ERROR_PRODUCT_ID_IS_INVALID = "ProductId is invalid";
    public static final String VALIDATION_ERROR_SHOPPING_CART_ID_IS_INVALID = "ShoppingCartId is invalid";
    public static final String VALIDATION_ERROR_SHOPPING_CART_ITEM_ID_IS_INVALID = "ShoppingCartItemId is invalid";

    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";
    public static final String VALIDATION_ERROR_BIRTHDATE_IS_INVALID = "BirthDate is invalid";

    public static final String VALIDATION_ERROR_FULLNAME_IS_INVALID = "FullName is invalid";
    public static final String VALIDATION_ERROR_FIRSTNAME_IS_INVALID = "Firstname is invalid";
    public static final String VALIDATION_ERROR_LASTNAME_IS_INVALID = "Lastname is invalid";

    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";
    public static final String VALIDATION_ERROR_EMAIL_IN_USE = "O email %s já está em uso por outro cliente";

    public static final String VALIDATION_ERROR_DOCUMENT_IS_INVALID = "Document is invalid";

    public static final String VALIDATION_ERROR_PHONE_IS_INVALID = "Phone is invalid";

    public static final String VALIDATION_ERROR_RECIPIENT_IS_INVALID = "Recipient is invalid";

    public static final String VALIDATION_ERROR_ORDER_DELIVERY_DATE_BE_INVALID = "Expected delivery date is invalid";

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

    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is archived";
    public static final String ERROR_CUSTOMER_NOT_FOUND = "Customer %s not found";
    public static final String ERROR_CUSTOMER_ALREADY_HAVE_ACTIVE_CART = "Customer %s already have a active shopping cart";

    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Order %s cannot change status from %s to %s";

    public static final String ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST = "Order %s cannot have delivery date in the past";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS = "Order %s cannot be placed, it has no items";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING = "Order %s cannot be placed, it has no shipping info";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO = "Order %s cannot be placed, it has no billing info";
    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD = "Order %s cannot be placed, it has no payment method";

    public static final String ERROR_ORDER_DOES_NOT_CONTAIN_ORDER_ITEM = "Order %s does not contain order item %s";

    public static final String ERROR_PRODUCT_OUT_OF_STOCK = "Product %s is out of stock";
    public static final String ERROR_PRODUCT_NOT_FOUND = "Product %s not found";

    public static final String ERROR_ORDER_CANNOT_BE_EDITED = "Order %s with status %s cannot be edited";

    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM = "ShoppingCart %s does not contain item %s";
    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT = "ShoppingCart %s does not contain product %s";
    public static final String ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT = "ShoppingCartItem %s is incompatible with product %s";
    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_VALID_ITEMS = "Shopping cart is empty or contains unavailable items";
    public static final String ERROR_SHOPPING_CART_NOT_FOUND = "ShoppingCart %s not found";

    public static final String ERROR_ORDER_DOES_NOT_BELONGS_TO_CUSTOMER = "Order does not belong to the customer";
    public static final String ERROR_ORDER_IS_NOT_READY_TO_ADD_LOYALTY_POINTS = "Can't add loyalty points to an order that is not ready";
    public static final String ERROR_ORDER_NOT_FOUND =  "Order not found";
}
