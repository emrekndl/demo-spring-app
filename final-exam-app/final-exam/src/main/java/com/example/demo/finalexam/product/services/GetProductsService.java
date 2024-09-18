package com.example.demo.finalexam.product.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.web.PagedModel;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.IQuery;
import com.example.demo.finalexam.helpers.SortingAndPagingHelper;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.dto.ProductQueryParams;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.repository.IProductRepository;

@Service
public class GetProductsService implements IQuery<ProductQueryParams, PagedModel<EntityModel<ProductDTO>>> {

    private IProductRepository productRepository;
    private SortingAndPagingHelper sortingAndPagingHelper;
    private PagedResourcesAssembler<ProductDTO> assembler;

    public GetProductsService(IProductRepository productRepository, SortingAndPagingHelper sortingAndPagingHelper,
            PagedResourcesAssembler<ProductDTO> assembler) {
        this.productRepository = productRepository;
        this.sortingAndPagingHelper = sortingAndPagingHelper;
        this.assembler = assembler;
    }

    @Override
    @Cacheable("products_cache")
    public ResponseEntity<PagedModel<EntityModel<ProductDTO>>> execute(ProductQueryParams queryParams) {
        Pageable pageable = sortingAndPagingHelper.createPageable(
                queryParams.getPage(),
                queryParams.getSize(),
                queryParams.getSortBy(),
                queryParams.getDirection());

        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductDTO> productDTOsPage = products.map(ProductDTO::new);
        PagedModel<EntityModel<ProductDTO>> pagedModel = assembler.toModel(productDTOsPage);

        return ResponseEntity.status(HttpStatus.OK).body(pagedModel);

    }

}
