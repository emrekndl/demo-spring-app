package com.example.demo.finalexam.product.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.finalexam.product.dto.CategoryDTO;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.services.CreateCategoryService;
import com.example.demo.finalexam.product.services.GetCategoriesService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/categories", produces = "application/json")
public class CategoryController {

    // TODO: update, delete, etc will be added.    
    private final GetCategoriesService getCategoriesService;
    private final CreateCategoryService createCategoryService;

    public CategoryController(GetCategoriesService getCategoriesService, CreateCategoryService createCategoryService) {
        this.getCategoriesService = getCategoriesService;
        this.createCategoryService = createCategoryService;
    }

    @GetMapping()
    public ResponseEntity<List<CategoryDTO>> getCategory() {
        return getCategoriesService.execute(null);
    }

    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody Category category) {
        return createCategoryService.execute(category);
    }

}
