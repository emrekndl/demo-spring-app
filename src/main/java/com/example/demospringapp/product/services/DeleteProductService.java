package com.example.demospringapp.product.services;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demospringapp.ICommand;
import com.example.demospringapp.exceptions.ProductNotFoundException;
import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.Product;

@Service
public class DeleteProductService implements ICommand<Integer, Void> {
    private IProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(DeleteProductService.class);

    public DeleteProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Integer id) {
        logger.info("Executing " + getClass() + " with id: " + id);
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        // throw a exception.
        throw new ProductNotFoundException(id);
    }
}
