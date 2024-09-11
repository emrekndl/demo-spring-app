package com.example.demospringapp.product.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demospringapp.ICommand;
import com.example.demospringapp.exceptions.ProductNotFoundException;
import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.Product;
import com.example.demospringapp.product.model.ProductDTO;
import com.example.demospringapp.product.model.UpdateProductCommand;
// import com.example.demospringapp.product.validators.ProductValidator;

@Service
public class UpdateProductService implements ICommand<UpdateProductCommand, ProductDTO> {
    private final IProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(UpdateProductService.class);

    public UpdateProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    // @CacheEvict(value = "productCache", key = "#command.getId()")
    @CachePut(value = "productCache", key = "#command.getId()")
    // Evict -> throws it away only
    // Put -> throws it away then puts the return value of the method into the cache
    public ResponseEntity<ProductDTO> execute(UpdateProductCommand command) {
        logger.info("Executing " + getClass() + " with command: " + command);
        Optional<Product> productOptional = productRepository.findById(command.getId());
        if (productOptional.isPresent()) {
            Product product = command.getProduct();
            product.setId(command.getId());
            // spring starter validation
            // ProductValidator.execute(product);
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(product));
        }
        // throw a exception.
        throw new ProductNotFoundException(command.getId());
    }
}
