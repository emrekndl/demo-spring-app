package com.example.demo.finalexam.product.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.IQuery;
import com.example.demo.finalexam.exceptions.ProductNotFoundException;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.repository.IProductRepository;
import com.example.demo.finalexam.profanityfilter.ProfanityFilterService;

@Service
public class GetProductService implements IQuery<UUID, ProductDTO> {
    private IProductRepository productRepository;
    private ProfanityFilterService profanityFilterService;

    public GetProductService(IProductRepository productRepository, ProfanityFilterService profanityFilterService) {
        this.productRepository = productRepository;
        this.profanityFilterService = profanityFilterService;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(UUID id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            Product filterProduct = profanityFilterService.execute(product.get());
            return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(filterProduct));
        }

        throw new ProductNotFoundException();
    }

}

