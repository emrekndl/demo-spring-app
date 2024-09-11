package com.example.demospringapp.product.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demospringapp.ICommand;
import com.example.demospringapp.product.model.Product;
import com.example.demospringapp.product.model.ProductDTO;
// import com.example.demospringapp.product.validators.ProductValidator;
import com.example.demospringapp.product.IProductRepository;

@Service
public class CreateProductService implements ICommand<Product, ProductDTO> {
    private final IProductRepository productRepository;


    private static final Logger logger = LoggerFactory.getLogger(CreateProductService.class);

    public CreateProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(Product product) {
        // Validate before saving
        // spring boot starter validation
        // ProductValidator.execute(product);

        logger.info("Executing " + getClass() + " with product: " + product);
        
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(savedProduct));
    }

}
