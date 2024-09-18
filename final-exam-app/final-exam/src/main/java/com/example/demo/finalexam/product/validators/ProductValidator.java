package com.example.demo.finalexam.product.validators;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.example.demo.finalexam.exceptions.CategoryNotFoundException;
import com.example.demo.finalexam.exceptions.ErrorMessages;
import com.example.demo.finalexam.exceptions.ProductNotFoundException;
import com.example.demo.finalexam.exceptions.ProductNotValidException;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.repository.ICategoryRepository;
import com.example.demo.finalexam.product.repository.IProductRepository;

@Component
public class ProductValidator {
    private ICategoryRepository categoryRepository;
    private IProductRepository productRepository;

    public ProductValidator(ICategoryRepository categoryRepository, IProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public void validate(Product product) {

        if (product.getName() == null || product.getName().isEmpty()) {
            throw new ProductNotValidException(ErrorMessages.PRODUCT_NAME_CANNOT_BE_EMPTY.getMessage());
        }

        if (product.getPrice() < 0) {
            throw new ProductNotValidException(ErrorMessages.PRODUCT_PRICE_MUST_BE_POSITIVE.getMessage());
        }

    }

    public Category validateAndGetCategory(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new CategoryNotFoundException());
    }
    
    public void validateProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException();
        }
    }
}