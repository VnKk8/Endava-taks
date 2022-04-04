package com.endava.school.supermarket.api.exception;

public class PaymentMethodNotValid extends RuntimeException{
    public PaymentMethodNotValid(String message) {
        super(message);
    }
}
