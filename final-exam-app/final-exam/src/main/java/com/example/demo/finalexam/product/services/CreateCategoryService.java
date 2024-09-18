package com.example.demo.finalexam.product.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.ICommand;
import com.example.demo.finalexam.product.dto.CategoryDTO;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.repository.ICategoryRepository;

@Service
public class CreateCategoryService implements ICommand<Category, CategoryDTO> {

    private ICategoryRepository categoryRepository;

    public CreateCategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<CategoryDTO> execute(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryDTO(savedCategory));
    }

}
