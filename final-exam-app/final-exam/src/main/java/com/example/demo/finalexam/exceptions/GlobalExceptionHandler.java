package com.example.demo.finalexam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomNotFoundException(ProductNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Product Not Found", ex.getMessage()));
    }

    @ExceptionHandler(ProductNotValidException.class)
    public ResponseEntity<ErrorResponse> handleProductNotValidException(ProductNotValidException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Validation Error", ex.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Category Not Found", ex.getMessage()));
    }

}
