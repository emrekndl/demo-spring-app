package com.example.demo.finalexam.product.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.finalexam.product.model.Product;

public class ProductSpecificationBuilder {

    private final List<Specification<Product>> specifications = new ArrayList<Specification<Product>>();

    public ProductSpecificationBuilder with(String key, Object value, SearchOperation operation) {
       if (value != null) {
            specifications.add(new ProductSpecification(new SearchCriteria(key, value, operation)));
       } 
        return this;
    }
    
    public Specification<Product> build() {
        if (specifications.isEmpty()) {
            return null;
        }    
        
        Specification<Product> result = specifications.get(0);
        
        for (int i = 1; i < specifications.size(); i++) {
            result = Specification.where(result).and(specifications.get(i));
        }
        
        return result;
    }


}
