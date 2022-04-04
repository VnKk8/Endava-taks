package com.endava.school.supermarket.api.exception;

public class CashCanNotBeNull extends RuntimeException{
    public CashCanNotBeNull(String message) {
        super(message);
    }
}
