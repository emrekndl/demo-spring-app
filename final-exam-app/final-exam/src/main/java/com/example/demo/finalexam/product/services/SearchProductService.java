package com.example.demo.finalexam.product.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.IQuery;
import com.example.demo.finalexam.helpers.SortingAndPagingHelper;
import com.example.demo.finalexam.product.IProductRepository;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.dto.ProductSearchParams;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.specification.ProductSpecificationBuilder;
import com.example.demo.finalexam.product.specification.SearchOperation;

@Service
public class SearchProductService implements IQuery<ProductSearchParams, Page<ProductDTO>> {
    private IProductRepository productRepository;
    private SortingAndPagingHelper sortingAndPagingHelper;

    public SearchProductService(IProductRepository productRepository, SortingAndPagingHelper sortingAndPagingHelper) {
        this.productRepository = productRepository;
        this.sortingAndPagingHelper = sortingAndPagingHelper;
    }

    @Override
    public ResponseEntity<Page<ProductDTO>> execute(ProductSearchParams searchParams) {

        ProductSpecificationBuilder productBuilder = new ProductSpecificationBuilder();

        productBuilder.with("name", searchParams.getName(), SearchOperation.NAME_OR_DESCRIPTION)
            .with("description", searchParams.getDescription(), SearchOperation.CONTAINS)
            .with("price", searchParams.getPrice(), SearchOperation.EQUALS)
            .with("manufacturer", searchParams.getManufacturer(), SearchOperation.EQUALS)
            .with("region", searchParams.getRegion(), SearchOperation.EQUALS)
            .with("category", searchParams.getCategory(), SearchOperation.EQUALS);

        Specification<Product> spec= productBuilder.build();
        
         Pageable pageable = sortingAndPagingHelper.createPageable(
                searchParams.getPage(),
                searchParams.getSize(),
                searchParams.getSortBy(),
                searchParams.getDirection());


        Page<Product> products =  productRepository.findAll(spec, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(products.map(ProductDTO::new));
    
    }
}
