package com.example.demospringapp.product.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demospringapp.exceptions.ProductNotFoundException;
import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.Product;

public class DeleteProductServiceTest {
    
    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private DeleteProductService deleteProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void given_product_exist_when_delete_product_service_return_no_content() {
        // Given
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setPrice(100.0);
        product.setDescription("Description test product 1");
        
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1);

        // When
        ResponseEntity<Void> responseEntity = deleteProductService.execute(1);        
        
        // Then
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        // verify(productRepository).deleteById(1);
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).deleteById(1);
        
    }
    
    @Test
    public void given_product_not_exist_when_delete_product_service_return_not_found() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // When&Then
        assertThrows(ProductNotFoundException.class, () -> deleteProductService.execute(1));
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(0)).deleteById(1);
    }

}
