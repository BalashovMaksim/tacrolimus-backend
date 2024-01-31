package com.tacrolimus.backend.exception;

public class InvalidFileUrlException extends RuntimeException{
    private static final String MESSAGE = "URL error";
    public InvalidFileUrlException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
