package com.tacrolimus.backend.exception;

import java.util.UUID;

public class SantaRegistrationNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Registration with ID: %s not found or already deleted";

    public SantaRegistrationNotFoundException(UUID id) {
        super(String.format(MESSAGE, id));
    }
}
