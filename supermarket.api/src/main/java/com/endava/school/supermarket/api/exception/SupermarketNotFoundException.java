package com.endava.school.supermarket.api.exception;

public class SupermarketNotFoundException extends RuntimeException {
    public SupermarketNotFoundException(String message) {
        super(message);
    }
}
