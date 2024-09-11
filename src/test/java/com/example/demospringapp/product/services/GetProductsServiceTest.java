package com.example.demospringapp.product.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.Product;
import com.example.demospringapp.product.model.ProductDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;


public class GetProductsServiceTest {
    
    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private GetProductsService getProductsService;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void given_list_products_exist_when_get_products_service_return_list_product_dtos() {
        
        // Given
        List<Product> expectedProducts = Arrays.asList(
                new Product(null, "Product 1", "Product descripton 1", 10.99),
                new Product(null, "Product 2", "Product description 2", 15.99)
        );
        
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // When
        ResponseEntity<List<ProductDTO>> actualProducts = getProductsService.execute(null);

        // Then
        assertEquals(ResponseEntity.ok(expectedProducts.stream().map(ProductDTO::new).toList()), actualProducts);
        verify(productRepository, times(1)).findAll();
    }  
    
    @Test
    public void given_list_products_is_empty_when_get_products_service_return_empty_list() {

        // Given
        List<Product> expectedProducts = Arrays.asList();

        when(productRepository.findAll()).thenReturn(expectedProducts);

        // When
        ResponseEntity<List<ProductDTO>> actualProducts = getProductsService.execute(null);

        // Then
        assertEquals(ResponseEntity.ok(expectedProducts.stream().map(ProductDTO::new).toList()), actualProducts);
        verify(productRepository, times(1)).findAll();
    }
}
