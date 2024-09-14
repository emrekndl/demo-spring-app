package com.example.demo.finalexam.product.services;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.IQuery;
import com.example.demo.finalexam.product.IProductRepository;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.model.ProductDTO;
import com.example.demo.finalexam.product.specification.ProductSpecificationBuilder;
import com.example.demo.finalexam.product.specification.SearchOperation;
import com.example.demo.finalexam.product.specification.SearchProductRequest;

@Service
public class SearchProductService implements IQuery<SearchProductRequest, List<ProductDTO>> {
    private IProductRepository productRepository;

    public SearchProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(SearchProductRequest searchRequest) {

        ProductSpecificationBuilder productBuilder = new ProductSpecificationBuilder();
        productBuilder.with("name", searchRequest.getName(), SearchOperation.NAME_OR_DESCRIPTION)
            .with("description", searchRequest.getDescription(), SearchOperation.CONTAINS)
            .with("price", searchRequest.getPrice(), SearchOperation.EQUALS)
            .with("manufacturer", searchRequest.getManufacturer(), SearchOperation.EQUALS)
            .with("region", searchRequest.getRegion(), SearchOperation.EQUALS)
            .with("category", searchRequest.getCategory(), SearchOperation.EQUALS);

        Specification<Product> spec= productBuilder.build();
        
        List<Product> products =  productRepository.findAll(spec);

        return ResponseEntity.status(HttpStatus.OK).body(products.stream().map(ProductDTO::new).toList());
    
        // return ResponseEntity.status(HttpStatus.OK).body(productRepository
        // .findByNameOrDescriptionContaining(nameOrDesc)
        // .stream()
        // .map(ProductDTO::new)
        // .toList());
    }
}
