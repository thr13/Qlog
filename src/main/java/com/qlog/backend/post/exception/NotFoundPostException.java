package com.qlog.backend.post.exception;

public class NotFoundPostException extends RuntimeException {
    public NotFoundPostException(String message) {
        super(message);
    }
}
