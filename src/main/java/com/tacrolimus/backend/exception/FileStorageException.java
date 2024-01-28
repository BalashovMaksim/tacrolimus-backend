package com.tacrolimus.backend.exception;

public class FileStorageException extends RuntimeException {
    private static final String MESSAGE = "Failed to store file: %s";

    public FileStorageException(String filename, Throwable cause) {
        super(String.format(MESSAGE, filename), cause);
    }
}

