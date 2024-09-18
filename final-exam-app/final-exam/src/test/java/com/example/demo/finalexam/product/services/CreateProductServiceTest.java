package com.example.demo.finalexam.product.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.repository.IProductRepository;
import com.example.demo.finalexam.product.validators.ProductValidator;

class CreateProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @InjectMocks
    private CreateProductService createProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulCreation() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(10.0);
        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        when(productValidator.validateAndGetCategory(anyString())).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            savedProduct.setId(UUID.randomUUID());
            return savedProduct;
        });

        ResponseEntity<ProductDTO> response = createProductService.execute(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Test Product", response.getBody().getName());
        assertEquals(10.0, response.getBody().getPrice());
        assertEquals("Test Category", response.getBody().getCategory().getName());

        verify(productValidator).validate(product);
        verify(productValidator).validateAndGetCategory("Test Category");
        verify(productRepository).save(product);
    }

    @Test
    void testExecute_ValidationFailure() {
        Product product = new Product();
        doThrow(new IllegalArgumentException("Invalid product")).when(productValidator).validate(product);

        assertThrows(IllegalArgumentException.class, () -> createProductService.execute(product));

        verify(productValidator).validate(product);
        verifyNoInteractions(productRepository);
    }

    @Test
    void testExecute_CategoryValidationFailure() {
        Product product = new Product();
        Category category = new Category();
        category.setName("Invalid Category");
        product.setCategory(category);

        when(productValidator.validateAndGetCategory("Invalid Category"))
                .thenThrow(new IllegalArgumentException("Invalid category"));

        assertThrows(IllegalArgumentException.class, () -> createProductService.execute(product));

        verify(productValidator).validate(product);
        verify(productValidator).validateAndGetCategory("Invalid Category");
        verifyNoInteractions(productRepository);
    }

    @Test
    void testExecute_RepositorySaveFailure() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(10.0);
        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        when(productValidator.validateAndGetCategory(anyString())).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> createProductService.execute(product));

        verify(productValidator).validate(product);
        verify(productValidator).validateAndGetCategory("Test Category");
        verify(productRepository).save(product);
    }
}
