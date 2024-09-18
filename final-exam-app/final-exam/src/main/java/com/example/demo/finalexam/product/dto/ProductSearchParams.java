package com.example.demo.finalexam.product.dto;

// import com.example.demo.finalexam.product.model.Category;

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
    // private Category category;
    private String category;
}
