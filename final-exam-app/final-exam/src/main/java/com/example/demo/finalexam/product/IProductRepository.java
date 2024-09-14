package com.example.demo.finalexam.product;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.example.demo.finalexam.product.model.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    // @Query("SELECT p FROM Product p WHERE p.name LIKE %:nameOrDesc% OR p.description LIKE %:nameOrDesc%")
    // List<Product> findByNameOrDescriptionContaining(@Param("nameOrDesc") String name);
}
