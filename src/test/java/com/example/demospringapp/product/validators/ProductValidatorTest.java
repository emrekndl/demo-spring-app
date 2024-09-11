package com.example.demospringapp.product.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.demospringapp.exceptions.ProductNotValidException;
import com.example.demospringapp.product.model.Product;

class ProductValidatorTest {

    private Product product;

    @BeforeEach
    void set_up() {
        product = new Product();
        product.setName("Test Product");
        product.setDescription("This is a test product description that is long enough.");
        product.setPrice(10.00);
    }

    @Test
    @DisplayName("Should pass validation for valid product")
    void should_pass_validation_for_valid_product() {
        assertDoesNotThrow(() -> ProductValidator.execute(product));
    }

    @Test
    @DisplayName("Should throw exception for product with empty name")
    void should_throw_exception_for_product_with_empty_name() {
        product.setName("");
        assertThrows(ProductNotValidException.class, () -> ProductValidator.execute(product));
    }

    @Test
    @DisplayName("Should throw exception for product with null name")
    void should_throw_exception_for_product_with_null_name() {
        product.setName(null);
        assertThrows(ProductNotValidException.class, () -> ProductValidator.execute(product));
    }

    @Test
    @DisplayName("Should throw exception for product with short description")
    void should_throw_exception_for_product_with_short_description() {
        product.setDescription("Too short");
        assertThrows(ProductNotValidException.class, () -> ProductValidator.execute(product));
    }

    @Test
    @DisplayName("Should throw exception for product with null price")
    void should_throw_exception_for_product_with_null_price() {
        product.setPrice(null);
        assertThrows(ProductNotValidException.class, () -> ProductValidator.execute(product));
    }

    @Test
    @DisplayName("Should throw exception for product with negative price")
    void should_throw_exception_for_product_with_negative_price() {
        product.setPrice(-1.00);
        assertThrows(ProductNotValidException.class, () -> ProductValidator.execute(product));
    }

    @Test
    @DisplayName("Should pass validation for product with zero price")
    void should_pass_validation_for_product_with_zero_price() {
        product.setPrice(0.00);
        assertDoesNotThrow(() -> ProductValidator.execute(product));
    }

    @Test
    @DisplayName("Should pass validation for product with description exactly 20 characters")
    void should_pass_validation_for_product_with_description_exactly_20_characters() {
        product.setDescription("12345678901234567890");
        assertDoesNotThrow(() -> ProductValidator.execute(product));
    }
}
