package com.example.demo.finalexam.product.dto;

import java.util.UUID;

import com.example.demo.finalexam.product.model.Product;
import com.example.demo.finalexam.product.model.Region;

import lombok.Data;

@Data
public class ProductDTO {

    private UUID id;
    private String name;
    private String description;
    private double price;
    private String manufacturer;
    private Region region;
    // private String region;
    // private String category;
    private CategoryDTO category;

    public ProductDTO(Product product) {
        // super(product.getCategory());
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.manufacturer = product.getManufacturer();
        // this.region = product.getRegion().name();
        this.region = product.getRegion();
        this.category = new CategoryDTO(product.getCategory());
        // this.category = product.getCategory();
        // this.category = product.getCategory().getName();
    }
}
