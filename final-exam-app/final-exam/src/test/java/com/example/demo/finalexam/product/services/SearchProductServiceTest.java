package com.example.demo.finalexam.product.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.finalexam.helpers.SortingAndPagingHelper;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.dto.ProductSearchParams;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.repository.IProductRepository;

class SearchProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private SortingAndPagingHelper sortingAndPagingHelper;

    @InjectMocks
    private SearchProductService searchProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulSearch() {
        // Arrange
        ProductSearchParams searchParams = new ProductSearchParams();
        searchParams.setName("Test Product");
        searchParams.setDescription("Test Description");
        searchParams.setPrice(100.0);
        searchParams.setManufacturer("Test Manufacturer");
        searchParams.setRegion("Test Region");
        searchParams.setCategory("Test Category");
        searchParams.setPage(0);
        searchParams.setSize(10);
        searchParams.setSortBy("price");
        searchParams.setDirection("ASC");

        Category category = new Category();
        category.setName("Test Category");

        Product product = new Product();
        product.setCategory(category);
        // Populate other properties of product as needed

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("price"));
        Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());

        when(sortingAndPagingHelper.createPageable(0, 10, "price", "ASC")).thenReturn(pageable);
        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(productPage);

        // Act
        ResponseEntity<Page<ProductDTO>> response = searchProductService.execute(searchParams);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void testExecute_WithNoResults() {
        // Arrange
        ProductSearchParams searchParams = new ProductSearchParams();
        searchParams.setName("Nonexistent Product");
        searchParams.setPage(0);
        searchParams.setSize(10);

        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        Page<Product> productPage = new PageImpl<>(new ArrayList<>(), pageable, 0);

        when(sortingAndPagingHelper.createPageable(0, 10, null, null)).thenReturn(pageable);
        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(productPage);

        // Act
        ResponseEntity<Page<ProductDTO>> response = searchProductService.execute(searchParams);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getTotalElements());
    }

    // @Test
    // void testExecute_WithInvalidSorting() {
    //     // Arrange
    //     ProductSearchParams searchParams = new ProductSearchParams();
    //     searchParams.setPage(0);
    //     searchParams.setSize(10);
    //     searchParams.setSortBy("invalidSortField");
    //     searchParams.setDirection("ASC");
    //
    //     Product product = new Product();
    //     Category category = new Category();
    //     category.setName("Test Category");
    //     product.setCategory(category);
    //     List<Product> productList = new ArrayList<>();
    //     productList.add(product);
    //
    //     Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
    //     Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());
    //
    //     // Mock ayarları
    //     when(sortingAndPagingHelper.createPageable(0, 10, "invalidSortField", "ASC")).thenReturn(pageable);
    //     when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(productPage);
    //
    //     // Act
    //     ResponseEntity<Page<ProductDTO>> response = searchProductService.execute(searchParams);
    //
    //     // Assert
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertNotNull(response.getBody());
    //     assertEquals(1, response.getBody().getTotalElements());
    // }

}
