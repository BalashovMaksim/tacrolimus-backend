package com.tacrolimus.backend.exception;

import java.util.UUID;

public class PersonAlreadyRegisteredException extends RuntimeException{
    private static final String MESSAGE = "Person with ID: %s is already registered of deleted.";

    public PersonAlreadyRegisteredException(UUID id) {
        super(String.format(MESSAGE, id));
    }
}