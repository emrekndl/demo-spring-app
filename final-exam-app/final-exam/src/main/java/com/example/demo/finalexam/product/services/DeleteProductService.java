package com.example.demo.finalexam.product.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.ICommand;
import com.example.demo.finalexam.product.repository.IProductRepository;
import com.example.demo.finalexam.product.validators.ProductValidator;

@Service
public class DeleteProductService implements ICommand<UUID, Void> {

    private IProductRepository productRepository;
    private ProductValidator productValidator;

    private static final Logger logger = LoggerFactory.getLogger(DeleteProductService.class);

    public DeleteProductService(IProductRepository productRepository, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
    }

    @Override
    public ResponseEntity<Void> execute(UUID id) {
        logger.info("Attempting to delete product with id: {}", id);
        productValidator.validateProduct(id);

        productRepository.deleteById(id);

        logger.info("Product deleted successfully with id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
