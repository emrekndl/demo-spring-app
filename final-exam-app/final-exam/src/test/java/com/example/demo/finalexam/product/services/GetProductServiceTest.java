package com.example.demo.finalexam.product.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.finalexam.exceptions.ProductNotFoundException;
import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.repository.IProductRepository;
import com.example.demo.finalexam.profanityfilter.ProfanityFilterService;

public class GetProductServiceTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ProfanityFilterService profanityFilterService;

    @InjectMocks
    private GetProductService getProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExecute_ProductFound_ReturnsFilteredProduct() {
        UUID id = UUID.randomUUID();
        Product product = new Product();
        product.setId(id);
        product.setName("Test Product");

        Category category = new Category();
        category.setName("Test Category");
        product.setCategory(category);

        Product filteredProduct = new Product();
        filteredProduct.setId(id);
        filteredProduct.setName("Filtered Product");
        filteredProduct.setCategory(category);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(profanityFilterService.execute(product)).thenReturn(filteredProduct);

        ResponseEntity<ProductDTO> response = getProductService.execute(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("Filtered Product", response.getBody().getName());
        assertEquals("Test Category", response.getBody().getCategory().getName());

        verify(productRepository).findById(id);
        verify(profanityFilterService).execute(product);
    }

    @Test
    public void testExecute_ProductNotFound_ThrowsException() {
        UUID id = UUID.randomUUID();

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> getProductService.execute(id));

        verify(productRepository).findById(id);
        verifyNoInteractions(profanityFilterService);
    }


} 