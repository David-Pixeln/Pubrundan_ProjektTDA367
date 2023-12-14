package com.Pubrunda.exception;

public class MissingRequiredAttributeException extends RuntimeException {

    public MissingRequiredAttributeException() {
        super("Missing required attribute");
    }

    public MissingRequiredAttributeException(String message) {
        super(message);
    }

}
