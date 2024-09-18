package com.example.demo.finalexam.exceptions;

public enum ErrorMessages {

    PRODUCT_NOT_FOUND("Product not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    PRODUCT_NAME_CANNOT_BE_EMPTY("Product name cannot be empty"),
    PRODUCT_DESCRIPTION_CANNOT_BE_EMPTY("Product description cannot be empty"),
    PRODUCT_PRICE_CANNOT_BE_EMPTY("Product price cannot be empty"),
    PRODUCT_PRICE_MUST_BE_POSITIVE("Product price must be positive"),
    PRODUCT_MANUFACTURER_CANNOT_BE_EMPTY("Product manufacturer cannot be empty"),
    PRODUCT_REGION_CANNOT_BE_EMPTY("Product region cannot be empty"),
    PRODUCT_CATEGORY_CANNOT_BE_EMPTY("Product category cannot be empty");

    private final String message;

    ErrorMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
