package com.example.demo.finalexam.product.model;

import java.util.UUID;

import lombok.Data;

@Data
public class ProductDTO {

    private UUID id;
    private String name;
    private String description;
    private long price;
    private String manufacturer;
    private Region region;
    private Category category;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.manufacturer = product.getManufacturer();
        this.region = product.getRegion();
        this.category = product.getCategory();
    }
}
