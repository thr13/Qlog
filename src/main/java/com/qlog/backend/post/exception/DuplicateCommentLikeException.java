package com.qlog.backend.post.exception;

public class DuplicateCommentLikeException extends RuntimeException {
    public DuplicateCommentLikeException(String message) {
        super(message);
    }
}
