package com.example.demo.finalexam.product.model;

public enum Region {

    US("US", "United States"),
    CA("CA", "Canada");
    
    private final String code;
    private final String description;

    Region(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
}
