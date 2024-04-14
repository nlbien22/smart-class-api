package com.capstone.smartclassapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseMessage notFoundException(ResourceNotFoundException ex) {
        return ResponseMessage.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = ResourceBadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseMessage badRequestException(ResourceBadRequestException ex) {
        return ResponseMessage.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = ResourceConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseMessage conflictException(ResourceConflictException ex) {
        return ResponseMessage.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getMessage())
                .build();
    }

    @ExceptionHandler(value = NotAllowAction.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseMessage notAllowAction(NotAllowAction ex) {
        return ResponseMessage.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .error(ex.getMessage())
                .build();
    }
}
