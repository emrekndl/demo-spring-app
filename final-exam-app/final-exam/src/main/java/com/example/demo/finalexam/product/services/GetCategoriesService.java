package com.example.demo.finalexam.product.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.IQuery;
import com.example.demo.finalexam.product.dto.CategoryDTO;
import com.example.demo.finalexam.product.model.Category;
import com.example.demo.finalexam.product.repository.ICategoryRepository;

@Service
public class GetCategoriesService implements IQuery<Void, List<CategoryDTO>> {
    
    private ICategoryRepository categoryRepository;

    public GetCategoriesService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ResponseEntity<List<CategoryDTO>> execute(Void input) {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categories.stream().map(CategoryDTO::new).toList());
    }

}



