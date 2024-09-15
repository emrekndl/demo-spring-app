package com.example.demo.finalexam.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchParams extends ProductQueryParams{

    private String name;
    private String description;
    private Double price;
    private String manufacturer;
    private String region;
    private String category;
}
