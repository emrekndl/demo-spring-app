package com.example.demo.finalexam.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.ICommand;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.model.UpdateProductCommand;
import com.example.demo.finalexam.product.repository.IProductRepository;
import com.example.demo.finalexam.product.validators.ProductValidator;

@Service
public class UpdateProductService implements ICommand<UpdateProductCommand, ProductDTO> {

    private IProductRepository productRepository;
    private ProductValidator productValidator;
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateProductService.class);

    public UpdateProductService(IProductRepository productRepository,
            ProductValidator productValidator) {
        this.productValidator = productValidator;
        this.productRepository = productRepository;
    }

    @Override
    /**
     * Caches the updated product in the "products_cache" cache, using the product ID as the key.
     * This ensures that the updated product information is available in the cache for subsequent requests.
     */
    @CachePut(value = "products_cache", key = "#command.getId()")
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command) {

        productValidator.validateProduct(command.getId());
        Product product = command.getProduct();
        Category category = productValidator.validateAndGetCategory(product.getCategory().getName());
        product.setId(command.getId());
        product.setCategory(category);
        productValidator.validate(product);

        Product savedProduct = productRepository.save(product);

        logger.info("Product updated successfully with id: {}", savedProduct.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(savedProduct));

    }

}
