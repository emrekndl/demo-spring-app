package com.example.demo.finalexam.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductQueryParams {

    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
}
