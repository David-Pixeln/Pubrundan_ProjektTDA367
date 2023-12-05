package com.Pubrunda.pub;

public class PubNotFoundException extends RuntimeException {
    PubNotFoundException(Long id) {
        super("Could not find pub " + id);
    }
}
