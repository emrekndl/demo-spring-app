package com.example.demo.finalexam.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(CategoryNotFoundException.class);
    
    public CategoryNotFoundException() {
        super(ErrorMessages.CATEGORY_NOT_FOUND.getMessage());
        logger.error(ErrorMessages.CATEGORY_NOT_FOUND.getMessage());
    }

}
