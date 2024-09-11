package com.example.demospringapp.product;

import org.springframework.web.bind.annotation.RestController;

import com.example.demospringapp.product.model.Product;
import com.example.demospringapp.product.model.ProductDTO;
import com.example.demospringapp.product.model.UpdateProductCommand;
import com.example.demospringapp.product.services.CreateProductService;
import com.example.demospringapp.product.services.DeleteProductService;
import com.example.demospringapp.product.services.GetProductService;
import com.example.demospringapp.product.services.GetProductsService;
import com.example.demospringapp.product.services.SearchProductService;
import com.example.demospringapp.product.services.UpdateProductService;

import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ProductController {
    // @Autowired
    private final CreateProductService createProductService;
    private final GetProductsService getProductsService;
    private final SearchProductService searchProductService;
    private final GetProductService getProductService;
    private final UpdateProductService updateProductSerivce;
    private final DeleteProductService deleteProductService;

    public ProductController(CreateProductService createProductService, GetProductsService getProductsService,
            GetProductService getProductService, UpdateProductService updateProductSerivce,
            DeleteProductService deleteProductService, SearchProductService searchProductService) {
        this.createProductService = createProductService;
        this.getProductsService = getProductsService;
        this.searchProductService = searchProductService;
        this.getProductService = getProductService;
        this.updateProductSerivce = updateProductSerivce;
        this.deleteProductService = deleteProductService;
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody Product product) {
        return createProductService.execute(product);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return getProductsService.execute(null);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductDTO>> searchProductByName(@RequestParam String name) {

        return searchProductService.execute(name);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        return getProductService.execute(id);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return updateProductSerivce.execute(new UpdateProductCommand(id, product));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        return deleteProductService.execute(id);
    }

}
