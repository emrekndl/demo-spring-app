package com.example.demo.finalexam.product.controllers;

import java.util.UUID;

import org.springframework.data.domain.Page;
// import org.springframework.data.web.PagedModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.dto.ProductQueryParams;
import com.example.demo.finalexam.product.dto.ProductSearchParams;
import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.model.UpdateProductCommand;
import com.example.demo.finalexam.product.services.CreateProductService;
import com.example.demo.finalexam.product.services.DeleteProductService;
import com.example.demo.finalexam.product.services.GetProductService;
import com.example.demo.finalexam.product.services.GetProductsService;
import com.example.demo.finalexam.product.services.SearchProductService;
import com.example.demo.finalexam.product.services.UpdateProductService;

@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private final GetProductService getProductService;
    private final GetProductsService getProductsService;
    private final SearchProductService searchProductService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;
    private final DeleteProductService deleteProductService;

    public ProductController(GetProductService getProductService, GetProductsService getProductsService,
            SearchProductService searchProductService, CreateProductService createProductService,
            UpdateProductService updateProductService, DeleteProductService deleteProductService) {
        this.getProductService = getProductService;
        this.getProductsService = getProductsService;
        this.searchProductService = searchProductService;
        this.createProductService = createProductService;
        this.updateProductService = updateProductService;
        this.deleteProductService = deleteProductService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        return getProductService.execute(id);
    }

    @GetMapping()
    // GET /products?page=1&size=10&sortBy=price&direction=desc
    public ResponseEntity<PagedModel<EntityModel<ProductDTO>>> getProducts(@ModelAttribute ProductQueryParams queryParams) {
            // @RequestParam(required = false) Integer page,
            // @RequestParam(required = false) Integer size,
            // @RequestParam(required = false) String sortBy,
            // @RequestParam(required = false) String direction) {

        return getProductsService.execute(queryParams);
    }

    @GetMapping("/search")
    // GET /products/search?page=1&size=20&sortBy=price&direction=asc
    public ResponseEntity<Page<ProductDTO>> searchProducts(@ModelAttribute ProductSearchParams searchParams) {
        
            // @RequestParam(name = "name", required = false) String name,
            // @RequestParam(name = "description", required = false) String description,
            // @RequestParam(name = "price", required = false) Double price,
            // @RequestParam(name = "manufacturer", required = false) String manufacturer,
            // @RequestParam(name = "category", required = false) String category,
            // @RequestParam(name = "region", required = false) String region) {

        return searchProductService.execute(searchParams);
    }
    
    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        return createProductService.execute(product);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        return updateProductService.execute(new UpdateProductCommand(id, product)); 
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        return deleteProductService.execute(id);
    }
}
