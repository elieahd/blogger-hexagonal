package com.devt.blogger.controllers;

import com.devt.blogger.dtos.ErrorResponse;
import com.devt.blogger.exceptions.CategoryNotFoundByIdException;
import com.devt.blogger.exceptions.PostNotFoundByIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({
            PostNotFoundByIdException.class,
            CategoryNotFoundByIdException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex) {
        LOGGER.warn("[404:{}] {}", ex.getClass().getSimpleName(), ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(new ErrorResponse(NOT_FOUND.name(), ex.getMessage()));
    }
}