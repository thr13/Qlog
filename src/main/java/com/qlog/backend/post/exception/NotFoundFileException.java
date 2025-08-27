package com.qlog.backend.post.exception;

public class NotFoundFileException extends RuntimeException {
    public NotFoundFileException(String message) {
        super(message);
    }
}
