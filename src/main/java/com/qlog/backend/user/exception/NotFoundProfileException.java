package com.qlog.backend.user.exception;

public class NotFoundProfileException extends RuntimeException {
    public NotFoundProfileException(String message) {
        super(message);
    }
}
