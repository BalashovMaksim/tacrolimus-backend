package com.tacrolimus.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RecipientNotFoundException extends ResponseStatusException {
    private static final String DEFAULT_MESSAGE = "Santa not found or no recipient assigned";

    public RecipientNotFoundException(HttpStatus status) {
        super(status, DEFAULT_MESSAGE);
    }
}
