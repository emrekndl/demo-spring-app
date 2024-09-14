package com.example.demo.finalexam.product.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchProductRequest {

    private String name;
    private String description;
    private Double price;
    private String manufacturer;
    private String region;
    private String category;
}
