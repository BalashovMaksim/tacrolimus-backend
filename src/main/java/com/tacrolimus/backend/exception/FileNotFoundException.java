package com.tacrolimus.backend.exception;

import java.util.UUID;

public class FileNotFoundException extends RuntimeException{
    private static final String MESSAGE = "File with ID: %s not found";

    public FileNotFoundException(UUID id) {
        super(String.format(MESSAGE, id));
    }
}
