package com.example.demo.finalexam.product.dto;

import com.example.demo.finalexam.product.model.Category;

import lombok.Data;

@Data
public class CategoryDTO {
    
    private String name;

    public CategoryDTO(Category category) {
        this.name = category.getName();
    }

}
