package com.qlog.backend.post.exception;

public class DuplicateCategoryException extends RuntimeException {
  public DuplicateCategoryException(String message) {
    super(message);
  }
}
