package com.example.demo.finalexam.product.services;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.IQuery;
import com.example.demo.finalexam.helpers.SortingAndPagingHelper;
import com.example.demo.finalexam.product.IProductRepository;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.dto.ProductQueryParams;
import com.example.demo.finalexam.product.model.Product;

@Service
public class GetProductsService implements IQuery<ProductQueryParams, Page<ProductDTO>> {

    private IProductRepository productRepository;
    private SortingAndPagingHelper sortingAndPagingHelper;

    public GetProductsService(IProductRepository productRepository, SortingAndPagingHelper sortingAndPagingHelper) {
        this.productRepository = productRepository;
        this.sortingAndPagingHelper = sortingAndPagingHelper;
    }

    @Override
    @Cacheable("products_cache")
    public ResponseEntity<Page<ProductDTO>> execute(ProductQueryParams queryParams) {
        Pageable pageable = sortingAndPagingHelper.createPageable(
                queryParams.getPage(),
                queryParams.getSize(),
                queryParams.getSortBy(),
                queryParams.getDirection());

        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductDTO> productDTOsPage = products.map(ProductDTO::new);

        return ResponseEntity.status(HttpStatus.OK).body(productDTOsPage);

    }

}
