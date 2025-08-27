package com.qlog.backend.post.exception;

public class NotFoundCommentException extends RuntimeException {
    public NotFoundCommentException(String message) {
        super(message);
    }
}
