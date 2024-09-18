package com.example.demo.finalexam.product.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UpdateProductCommand {
    private UUID id;
    private Product product;

    public UpdateProductCommand(UUID id, Product product) {
        this.id = id;
        this.product = product;
    }
}
