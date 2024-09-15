package com.example.demo.finalexam.product;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.finalexam.product.dto.ProductDTO;
import com.example.demo.finalexam.product.dto.ProductQueryParams;
import com.example.demo.finalexam.product.dto.ProductSearchParams;
import com.example.demo.finalexam.product.services.GetProductService;
import com.example.demo.finalexam.product.services.GetProductsService;
import com.example.demo.finalexam.product.services.SearchProductService;

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
    // GET /products?page=1&size=10&sortBy=price&direction=desc
    public ResponseEntity<Page<ProductDTO>> getProducts(@ModelAttribute ProductQueryParams queryParams) {
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
}
