package com.endava.school.supermarket.api.common;

public class ExceptionMessages {
    public static final String SUPERMARKET_NOT_FOUND = "No supermarket was found with ID:";
    public static final String ITEM_NOT_FOUND = "No item was found with ID:";
    public static final String PAYMENT_METHOD_IS_NOT_VALID = "Payment method is incorrect! Only CASH or CARD are allowed";
    public static final String CASH_CAN_NOT_BE_NULL = "Cash amount can not be zero or null when you choose cash settlement";
    public static final String PURCHASES_NOT_FOUND_WITH_CRITERIA = "No purchases was found with that criteria";
    public static final String CANNOT_EXPORT_FILE = "The file cannot be export";
    public static final String SUCCESSFUL_EXPORT = "The file exported be successfully";
}
