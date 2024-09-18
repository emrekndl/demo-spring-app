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

import com.example.demo.finalexam.exceptions.ProductNotFoundException;
import com.example.demo.finalexam.product.repository.IProductRepository;
import com.example.demo.finalexam.product.validators.ProductValidator;

class DeleteProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @InjectMocks
    private DeleteProductService deleteProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_SuccessfulDeletion() {
        // Arrange
        UUID productId = UUID.randomUUID();

        // Mocking the validator behavior
        doNothing().when(productValidator).validateProduct(productId);
        
        // Mocking the repository behavior
        doNothing().when(productRepository).deleteById(productId);

        // Act
        ResponseEntity<Void> response = deleteProductService.execute(productId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(productValidator).validateProduct(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void testExecute_ProductNotFound() {
        // Arrange
        UUID productId = UUID.randomUUID();

        // Mocking the validator to throw an exception
        doThrow(new ProductNotFoundException()).when(productValidator).validateProduct(productId);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> deleteProductService.execute(productId));

        verify(productValidator).validateProduct(productId);
        verifyNoMoreInteractions(productRepository); // deleteById should not be called
    }
}
