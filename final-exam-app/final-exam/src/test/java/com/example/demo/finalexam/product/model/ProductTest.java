package com.example.demo.finalexam.product.model;

import java.util.UUID;

public class ProductTest {
    public static void main(String[] args) {
        Category category = new Category();
        category.setId(1); 
        category.setName("Electronics");

        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setDescription("High-quality wireless headphones");
        product.setPrice(29999); 
        product.setManufacturer("AudioTech");
        product.setCategory(category); 
        product.setRegion(Region.US); 

        System.out.println("Product ID: " + product.getId());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Manufacturer: " + product.getManufacturer());
        System.out.println("Category: " + product.getCategory().getName());
        System.out.println("Region: " + product.getRegion().getDescription());
    }
}
