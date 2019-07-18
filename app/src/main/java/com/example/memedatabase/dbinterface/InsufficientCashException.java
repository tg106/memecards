package com.example.memedatabase.dbinterface;

public class InsufficientCashException extends RuntimeException {
    public InsufficientCashException(String errorMessage) {
        super(errorMessage);
    }
}
