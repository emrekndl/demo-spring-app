package com.example.demospringapp.product.validators;

import org.springframework.util.StringUtils;

import com.example.demospringapp.exceptions.ErrorMessages;
import com.example.demospringapp.exceptions.ProductNotValidException;
import com.example.demospringapp.product.model.Product;

public class ProductValidator {

    private ProductValidator() {
    }

    public static void execute(Product product) {

        // StringUtils.isEmpty() 
        if (!StringUtils.hasLength(product.getName())) {
            throw new ProductNotValidException(ErrorMessages.NAME_IS_REQUIRED.getMessage());
        }

        if (product.getDescription().length() < 20) {
            throw new ProductNotValidException(ErrorMessages.DESCRIPTION_LENGTH.getMessage());
        }

        if (product.getPrice() == null || product.getPrice() < 0.00) {
            throw new ProductNotValidException(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage());
        }
    }
}
