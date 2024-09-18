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
import com.example.demo.finalexam.product.model.UpdateProductCommand;
import com.example.demo.finalexam.product.repository.IProductRepository;
import com.example.demo.finalexam.product.validators.ProductValidator;
import com.example.demo.finalexam.exceptions.ProductNotFoundException;
import com.example.demo.finalexam.exceptions.CategoryNotFoundException;

class UpdateProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @InjectMocks
    private UpdateProductService updateProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulUpdate() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setName("Updated Product");
        Category category = new Category();
        category.setName("Electronics");
        product.setCategory(category);

        UpdateProductCommand command = new UpdateProductCommand(productId, product);

        // Mocking the validator and repository behavior
        doNothing().when(productValidator).validateProduct(productId);
        when(productValidator.validateAndGetCategory("Electronics")).thenReturn(category);
        doNothing().when(productValidator).validate(product);
        when(productRepository.save(product)).thenReturn(product);

        // Act
        ResponseEntity<ProductDTO> response = updateProductService.execute(command);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Product", response.getBody().getName());
        assertEquals("Electronics", response.getBody().getCategory().getName());

        verify(productValidator).validateProduct(productId);
        verify(productValidator).validateAndGetCategory("Electronics");
        verify(productValidator).validate(product);
        verify(productRepository).save(product);
    }

    @Test
    void testExecute_InvalidProductId() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        UpdateProductCommand command = new UpdateProductCommand(productId, product);

        // Mocking that the product ID is invalid
        doThrow(new ProductNotFoundException()).when(productValidator).validateProduct(productId);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> updateProductService.execute(command));

        // Verifications
        verify(productValidator).validateProduct(productId);
        verifyNoInteractions(productRepository);
    }

    @Test
    void testExecute_InvalidCategory() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        Category category = new Category();
        category.setName("InvalidCategory");
        product.setCategory(category);

        UpdateProductCommand command = new UpdateProductCommand(productId, product);

        // Mocking that the category is invalid
        doNothing().when(productValidator).validateProduct(productId);
        when(productValidator.validateAndGetCategory("InvalidCategory")).thenThrow(new CategoryNotFoundException());

        // Act & Assert
        assertThrows(CategoryNotFoundException.class, () -> updateProductService.execute(command));

        // Verifications
        verify(productValidator).validateProduct(productId);
        verify(productValidator).validateAndGetCategory("InvalidCategory");
        verifyNoInteractions(productRepository);
    }

    @Test
    void testExecute_InvalidProduct() {
        // Arrange
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        Category category = new Category();
        category.setName("Electronics");
        product.setCategory(category);

        UpdateProductCommand command = new UpdateProductCommand(productId, product);

        // Mocking that the product is invalid
        doNothing().when(productValidator).validateProduct(productId);
        when(productValidator.validateAndGetCategory("Electronics")).thenReturn(category);
        doThrow(new IllegalArgumentException("Invalid product")).when(productValidator).validate(product);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> updateProductService.execute(command));

        // Verifications
        verify(productValidator).validateProduct(productId);
        verify(productValidator).validateAndGetCategory("Electronics");
        verify(productValidator).validate(product);
        verifyNoInteractions(productRepository);
    }
}
