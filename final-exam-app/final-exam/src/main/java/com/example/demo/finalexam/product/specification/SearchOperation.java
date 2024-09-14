package com.example.demo.finalexam.product.specification;

public enum SearchOperation {

    EQUALS,        // "=" 
    NOT_EQUALS,    // "!="
    GREATER_THAN,  // ">"
    LESS_THAN,     // "<"
    CONTAINS,      // "%LIKE%"
    STARTS_WITH,   // "LIKE%..."
    ENDS_WITH,      // "...LIKE%"
    // Name or Descripton search
    NAME_OR_DESCRIPTION     // LIKE %nameOrDesc% or LIKE %nameOrDesc%
}
