package com.tacrolimus.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DrawOperationNotAllowedException extends ResponseStatusException {
    private static final String DEFAULT_MESSAGE = "Draw operation is not allowed because pairs already exist.";

    public DrawOperationNotAllowedException(HttpStatus status) {
        super(status, DEFAULT_MESSAGE);
    }
}
