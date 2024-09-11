package com.example.demospringapp.product.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demospringapp.IQuery;
import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.ProductDTO;

@Service
public class SearchProductService implements IQuery<String, List<ProductDTO>> {

    private IProductRepository productRepository;

    public SearchProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(String name) {
        return ResponseEntity.ok(productRepository.findByNameOrDescriptionContaining(name).stream().map(ProductDTO::new).toList());
        // return ResponseEntity.ok(productRepository.findByNameContaining(name).stream().map(ProductDTO::new).toList());
    }
    
}
