package com.example.demospringapp.product.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demospringapp.IQuery;
import com.example.demospringapp.exceptions.ProductNotFoundException;
import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.Product;
import com.example.demospringapp.product.model.ProductDTO;

@Service
public class GetProductService implements IQuery<Integer, ProductDTO> {
    private final IProductRepository productRespository;

    private static final Logger logger = LoggerFactory.getLogger(GetProductService.class);

    public GetProductService(IProductRepository productRespository) {
        this.productRespository = productRespository;
    }

    @Override
    @Cacheable("productCache")
    public ResponseEntity<ProductDTO> execute(Integer input) {
        logger.info("Executing " + getClass() + " with input " + input);
        Optional<Product> productOptional = productRespository.findById(input);
        if (productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(productOptional.get()));
        }
        // Throw a Product Not Found Exception.
        throw new ProductNotFoundException(input);
    }

}
