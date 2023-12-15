package com.Pubrunda.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String errorMessage) {
        super(errorMessage);
    }
}
