package com.tacrolimus.backend.exception;

public class FileNotReadableException extends RuntimeException {
    private static final String MESSAGE = "Unable to read file";
    public FileNotReadableException() {
        super(MESSAGE);
    }
}
