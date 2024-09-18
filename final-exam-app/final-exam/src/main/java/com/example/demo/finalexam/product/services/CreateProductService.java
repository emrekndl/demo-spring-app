package com.example.demo.finalexam.product.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.ICommand;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.repository.IProductRepository;
import com.example.demo.finalexam.product.validators.ProductValidator;

@Service
public class CreateProductService implements ICommand<Product, ProductDTO> {

    private IProductRepository productRepository;
    private ProductValidator productValidator;
    
    private static final Logger logger = LoggerFactory.getLogger(CreateProductService.class);

    public CreateProductService(IProductRepository productRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(Product product) {

        productValidator.validate(product);
        Category category = productValidator.validateAndGetCategory(product.getCategory().getName());

        product.setCategory(category);
        product.setId(UUID.randomUUID());
        Product savedProduct = productRepository.save(product);

        logger.info("Product created successfully with id: {}", savedProduct.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(savedProduct));
    }
}
