package com.example.demospringapp.product.headers;

import org.springframework.web.bind.annotation.RestController;

import com.example.demospringapp.product.model.Product;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;



@RestController
public class HeaderController {

    @GetMapping("/header")
    public String getRegionalResponse(@RequestHeader(required = false, defaultValue = "US") String region) {
        // normally abstract this out into a service class
        if (region.equals("UK")) {
            return "Hello from UK";
        } else if (region.equals("US")) {
            return "Hello from US";
        }
        return "Country not supported.";
    }
    
    // Accept header for response data convert xml or json type
    @GetMapping(value = "/header/product", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Product> getProduct() {
        Product product = new Product(1, "Product 1", "Produc 1 is great product.", 10.99);
        return ResponseEntity.ok(product);
    }
    
}
