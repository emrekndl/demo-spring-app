package com.example.demospringapp.exceptions;

public enum ErrorMessages {

    PRODUCT_NOT_FOUND("Product Not Found."),
    PRODUCT_NOT_FOUND_WITH_ID("Product Not Found With Id: "),
    NAME_IS_REQUIRED("Name is required."),
    DESCRIPTION_LENGTH("Description should be at least 20 characters long."),
    PRICE_CANNOT_BE_NEGATIVE("Price cannot be negative.");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
