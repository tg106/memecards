package com.example.memedatabase;

public class InsufficientCashException extends RuntimeException {
    public InsufficientCashException(String errorMessage) {
        super(errorMessage);
    }
}
