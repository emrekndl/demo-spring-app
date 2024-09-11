package com.example.demospringapp.product.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demospringapp.product.IProductRepository;
import com.example.demospringapp.product.model.Product;
import com.example.demospringapp.product.model.ProductDTO;

// import jakarta.validation.ConstraintViolationException;

public class CreateProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private CreateProductService createProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_valid_product_when_create_product_service_then_return_new_product_dto() {
        // Given
        Product product = new Product(null, "Valid Name", "Valid description with more than twenty characters.", 9.99);
        Product expectedProduct = new Product(1, product.getName(), product.getDescription(), product.getPrice());

        when(productRepository.save(product)).thenReturn(expectedProduct);

        // When
        ResponseEntity<ProductDTO> response = createProductService.execute(product);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ProductDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(expectedProduct.getId(), body.getId());
        assertEquals(expectedProduct.getName(), body.getName());
        assertEquals(expectedProduct.getDescription(), body.getDescription());

        verify(productRepository, times(1)).save(product);
    }

    // @Test
    // public void given_invalid_product_when_create_product_service_then_return_bad_request() {
        // Given
        // Product product = new Product(null, "Invalid Name", "Invalid descasşdlfkjasşldkfription.", 9.99);
        //
        // when(productRepository.save(product)).thenReturn(ConstraintViolationException.class);
        // When & Then
        // assertEquals(HttpStatus.BAD_REQUEST, createProductService.execute(product).getStatusCode());
        // verify(productRepository, times(0)).save(product); // Save method should notbe called

    // }
}
