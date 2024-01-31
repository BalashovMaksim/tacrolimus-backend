package com.tacrolimus.backend.exception;

import java.util.UUID;

public class PersonNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Person with id %s not found or deleted";

    public PersonNotFoundException(UUID id) {
        super(String.format(MESSAGE, id));
    }
}
