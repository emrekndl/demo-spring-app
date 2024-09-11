package com.example.demospringapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(ProductNotFoundException.class);

    public ProductNotFoundException(Integer id) {
        // enum for this message.
        // super("Product not found with id: " + id);
        super(ErrorMessages.PRODUCT_NOT_FOUND_WITH_ID.getMessage() + id);
        logger.error("Exception " + getClass() + " thrown");
    }
}
