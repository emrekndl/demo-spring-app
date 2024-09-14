package com.example.demo.finalexam.product.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.IQuery;
import com.example.demo.finalexam.product.IProductRepository;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.model.ProductDTO;

@Service
public class GetProductsService implements IQuery<Void, List<ProductDTO>> {

    private IProductRepository productRepository;

    public GetProductsService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(Void input) {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                products.stream()
                        .map(ProductDTO::new)
                        .toList());
    }

}
