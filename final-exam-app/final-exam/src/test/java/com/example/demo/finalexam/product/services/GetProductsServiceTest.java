package com.example.demo.finalexam.product.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.finalexam.helpers.SortingAndPagingHelper;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.dto.ProductQueryParams;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.repository.IProductRepository;

class GetProductsServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private SortingAndPagingHelper sortingAndPagingHelper;

    @Mock
    private PagedResourcesAssembler<ProductDTO> assembler;

    @InjectMocks
    private GetProductsService getProductsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteWithValidParams() {
        ProductQueryParams queryParams = new ProductQueryParams();
        queryParams.setPage(0);
        queryParams.setSize(10);
        queryParams.setSortBy("name");
        queryParams.setDirection("ASC");

        Pageable pageable = mock(Pageable.class);
        when(sortingAndPagingHelper.createPageable(0, 10, "name", "ASC")).thenReturn(pageable);

        Category category = new Category();
        category.setName("Test Category");
        List<Product> productList = Arrays.asList(
                createProduct("Product 1", 10.0, category),
                createProduct("Product 2", 20.0, category));
        Page<Product> productPage = new PageImpl<>(productList);
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        PagedModel<EntityModel<ProductDTO>> pagedModel = mock(PagedModel.class);
        when(assembler.toModel(any())).thenReturn(pagedModel);

        ResponseEntity<PagedModel<EntityModel<ProductDTO>>> response = getProductsService.execute(queryParams);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(pagedModel, response.getBody());

        verify(sortingAndPagingHelper).createPageable(0, 10, "name", "ASC");
        verify(productRepository).findAll(pageable);
        verify(assembler).toModel(any());
    }

    private Product createProduct(String name, double price, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        return product;
    }

    @Test
    void testExecuteWithEmptyResult() {
        ProductQueryParams queryParams = new ProductQueryParams();
        queryParams.setPage(0);
        queryParams.setSize(10);

        Pageable pageable = mock(Pageable.class);
        when(sortingAndPagingHelper.createPageable(0, 10, null, null)).thenReturn(pageable);

        Page<Product> emptyPage = new PageImpl<>(Arrays.asList());
        when(productRepository.findAll(pageable)).thenReturn(emptyPage);

        PagedModel<EntityModel<ProductDTO>> emptyPagedModel = mock(PagedModel.class);
        when(assembler.toModel(any())).thenReturn(emptyPagedModel);

        ResponseEntity<PagedModel<EntityModel<ProductDTO>>> response = getProductsService.execute(queryParams);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(emptyPagedModel, response.getBody());

        verify(sortingAndPagingHelper).createPageable(0, 10, null, null);
        verify(productRepository).findAll(pageable);
        verify(assembler).toModel(any());
    }

    @Test
    void testExecuteWithLargePageSize() {
        ProductQueryParams queryParams = new ProductQueryParams();
        queryParams.setPage(0);
        queryParams.setSize(1000);

        Pageable pageable = mock(Pageable.class);
        when(sortingAndPagingHelper.createPageable(0, 1000, null, null)).thenReturn(pageable);
        Category category = new Category();
        category.setName("Test Category");
        List<Product> productList = Arrays.asList(
                createProduct("Product 1", 10.99, category),
                createProduct("Product 2", 15.99, category));
        Page<Product> productPage = new PageImpl<>(productList);
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        PagedModel<EntityModel<ProductDTO>> pagedModel = mock(PagedModel.class);
        when(assembler.toModel(any())).thenReturn(pagedModel);

        ResponseEntity<PagedModel<EntityModel<ProductDTO>>> response = getProductsService.execute(queryParams);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(pagedModel, response.getBody());

        verify(sortingAndPagingHelper).createPageable(0, 1000, null, null);
        verify(productRepository).findAll(pageable);
        verify(assembler).toModel(any());
    }
}
