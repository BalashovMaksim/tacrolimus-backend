package com.tacrolimus.backend.exception;

public class EmptyFileException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "File cannot be empty";

    public EmptyFileException() {
        super(DEFAULT_MESSAGE);
    }
}
