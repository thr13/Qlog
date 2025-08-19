package com.qlog.backend.global.exception;

import com.qlog.backend.global.dto.ErrorResponse;
import com.qlog.backend.user.exception.NotFoundProfileException;
import com.qlog.backend.user.exception.NotFoundUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundUserException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundUserException(NotFoundUserException e) {
        log.error("NotFoundUserException: {}", e.getMessage());

        final ErrorCode errorCode = ErrorCode.NOT_FOUND_USER;
        final ErrorResponse response = new ErrorResponse(errorCode);

        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    @ExceptionHandler(NotFoundProfileException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundProfileException(NotFoundProfileException e) {
        log.error("NotFoundProfileException: {}", e.getMessage());

        final ErrorCode errorCode = ErrorCode.NOT_FOUND_PROFILE;
        final ErrorResponse response = new ErrorResponse(errorCode);

        return new ResponseEntity<>(response, errorCode.getStatus());
    }
}
