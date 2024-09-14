package com.example.demo.finalexam.product;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.finalexam.product.model.ProductDTO;
import com.example.demo.finalexam.product.services.GetProductService;
import com.example.demo.finalexam.product.services.GetProductsService;
import com.example.demo.finalexam.product.services.SearchProductService;
import com.example.demo.finalexam.product.specification.SearchProductRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final GetProductService getProductService;
    private final GetProductsService getProductsService;
    private final SearchProductService searchProductService;

    public ProductController(GetProductService getProductService, GetProductsService getProductsService,
            SearchProductService searchProductService) {
        this.getProductService = getProductService;
        this.getProductsService = getProductsService;
        this.searchProductService = searchProductService;
    }
    
    @RequestMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        System.out.println("Product ID: " + id + " type: " + id.getClass());
        return getProductService.execute(id);
    } 
    
    @GetMapping()
    public ResponseEntity<List<ProductDTO>>  getProducts() {
        return getProductsService.execute(null);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>>  searchProducts(
        @RequestParam(name = "name", required = false) String name,
        @RequestParam(name = "description", required = false) String description,
        @RequestParam(name = "price", required = false) Double price,
        @RequestParam(name = "manufacturer", required = false) String manufacturer,
        @RequestParam(name = "category", required = false) String category,
        @RequestParam(name = "region", required = false) String region
    ) {

        SearchProductRequest requestSearch = new SearchProductRequest(name, description, price, manufacturer, region, category);
        return searchProductService.execute(requestSearch);
    }
}
