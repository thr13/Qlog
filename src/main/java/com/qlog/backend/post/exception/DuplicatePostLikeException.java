package com.qlog.backend.post.exception;

public class DuplicatePostLikeException extends RuntimeException {
  public DuplicatePostLikeException(String message) {
    super(message);
  }
}
