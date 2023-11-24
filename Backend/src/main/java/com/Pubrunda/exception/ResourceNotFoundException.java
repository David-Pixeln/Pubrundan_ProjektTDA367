package com.Pubrunda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such resource")
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(long id) {
        super("Resource with id " + id + " not found");
    }
}
