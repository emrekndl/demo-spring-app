package com.example.demospringapp.product;

import org.springframework.stereotype.Repository;

import com.example.demospringapp.product.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    
    // spring data jpa - search
    List<Product> findByNameContaining(String name);
    
    // jpql custom query
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> findByNameOrDescriptionContaining(@Param("keyword") String name);

}
