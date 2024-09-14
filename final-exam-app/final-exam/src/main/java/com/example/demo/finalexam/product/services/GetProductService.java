package com.example.demo.finalexam.product.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.IQuery;
import com.example.demo.finalexam.exceptions.ProductNotFoundException;
import com.example.demo.finalexam.product.IProductRepository;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.model.ProductDTO;

@Service
public class GetProductService implements IQuery<UUID, ProductDTO> {
    private IProductRepository productRepository;

    public GetProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        System.out.println("Optional Product: " + product);

        if (product.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(product.get()));
        }
        
        throw new ProductNotFoundException();
    }

}
