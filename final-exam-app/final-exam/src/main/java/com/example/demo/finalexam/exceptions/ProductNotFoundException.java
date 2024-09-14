package com.example.demo.finalexam.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ProductNotFoundException.class);

    public ProductNotFoundException() {
        super(ErrorMessages.PRODUCT_NOT_FOUND.getMessage());
        logger.error(ErrorMessages.PRODUCT_NOT_FOUND.getMessage());
    }
}
