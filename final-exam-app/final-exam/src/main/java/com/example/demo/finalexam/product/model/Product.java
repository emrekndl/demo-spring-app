package com.example.demo.finalexam.product.model;


import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "product")
public class Product extends BaseProduct {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private UUID id;
    
    @Column(name = "description", nullable = false, length = 100)
    private String description;
    
    @Column(name = "price", nullable = false)
    private long price;
    
    @Column(name = "manufacturer", nullable = false, length = 60)
    private String manufacturer;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    @Column(name = "Region", nullable = false, length = 60)
    @Enumerated(EnumType.STRING)
    private Region region;
}
