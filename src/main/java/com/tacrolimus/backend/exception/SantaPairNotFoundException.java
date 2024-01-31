package com.tacrolimus.backend.exception;

import java.util.UUID;

public class SantaPairNotFoundException extends RuntimeException{
    private static final String MESSAGE = "Santa pair not found with ID: %s";
    public SantaPairNotFoundException(UUID id){
        super(String.format(MESSAGE, id));
    }
}
