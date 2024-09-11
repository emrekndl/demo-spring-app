package com.example.demospringapp.product.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demospringapp.exceptions.ProductNotFoundException;
import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.Product;
import com.example.demospringapp.product.model.ProductDTO;

public class GetProductServiceTest {
    
    @Mock // Mock object for ProductRepository
    private IProductRepository productRepository;
    
    @InjectMocks // Inject the mocked ProductRepository into GetProductService
    private GetProductService getProductService;

    @BeforeEach // Set up mock behavior before each test case
    public void setUp() {
        // Initiliazes the repository and the service
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void given_product_exists_when_get_produt_service_return_product_dto(){
        // Arrange
        // Set up the mock behavior for the repository

        // Given
        Product product = new Product();
        product.setId(1);
        product.setName("Product test 1");
        product.setDescription("Description test 1");
        product.setPrice(10.0);

        // this says 'when' but it'a actually still setting up
        when(productRepository.findById(1)).thenReturn(Optional.of(product)); 


        // Act
        // Call the method under test

        // When
        // This is where the actual method call happens
        ResponseEntity<ProductDTO> response = getProductService.execute(1); // Assuming this is the method being tested

        
        // Assert
        // Verify the expected outcome
        
        // ... (e.g., verify the response body, status code, etc.)
        // Then
        // assertEquals(expected, actual)
        assertEquals(ResponseEntity.ok(new ProductDTO(product)), response);
        // asserts the product repository was only called once
        verify(productRepository, times(1)).findById(1);
    }
    
    @Test
    public void given_product_does_not_exist_when_get_product_service_throw_product_not_found_exception(){
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ProductNotFoundException.class, () -> getProductService.execute(1));
        verify(productRepository, times(1)).findById(1);
        // ResponseEntity<ProductDTO> response = getProductService.execute(1);
        //
        // assertEquals(ResponseEntity.notFound().build(), response);
        // verify(productRepository, times(1)).findById(1);
    }
}
