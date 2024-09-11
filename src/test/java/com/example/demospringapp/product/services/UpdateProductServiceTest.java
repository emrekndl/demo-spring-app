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
import com.example.demospringapp.product.model.UpdateProductCommand;


public class UpdateProductServiceTest {
    
    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private UpdateProductService updateProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_product_exist_when_update_product_service_then_return_product_dto() {
        // Given
        Product product = new Product();
        product.setId(1);
        product.setName("Product 1");
        product.setPrice(10.0);
        product.setDescription("Product description test 1");
        
        Product updatedProduct = new Product();
        updatedProduct.setId(1);
        updatedProduct.setName("Product 1 Updated");
        updatedProduct.setPrice(15.0);
        updatedProduct.setDescription("Product description test 1 Updated");
        
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(updatedProduct);

        // When
        ResponseEntity<ProductDTO> response = updateProductService.execute(new UpdateProductCommand(product.getId(), product));

        // Then
        assertEquals(ResponseEntity.ok(new ProductDTO(product)), response);
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(product);
    } 
    
    @Test
    public void given_product_not_exist_when_update_product_service_then_return_not_found() {
        // Given
        // Product product = new Product();
        // product.setName("Product Name");
        // product.setPrice(10.0);
        // product.setDescription("Product Description test 1");
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ProductNotFoundException.class, () -> updateProductService.execute(new UpdateProductCommand(1, null)));
        verify(productRepository, times(1)).findById(1);
    }
}
