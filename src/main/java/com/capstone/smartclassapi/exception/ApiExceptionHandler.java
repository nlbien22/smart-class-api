package com.capstone.smartclassapi.exception;

import org.apache.coyote.BadRequestException;
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
}
