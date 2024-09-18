package com.example.demo.finalexam.product.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.example.demo.finalexam.product.model.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductSpecification implements Specification<Product> {

    private final SearchCriteria searchCriteria;

    public ProductSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Product> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {

        switch (searchCriteria.getOperation()) {
            case EQUALS:
                if (searchCriteria.getKey().equals("category")) {
                    return criteriaBuilder.equal(root.join("category").get("name"), searchCriteria.getValue());
                }
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.get(searchCriteria.getKey()),
                        searchCriteria.getValue().toString());
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.get(searchCriteria.getKey()),
                        searchCriteria.getValue().toString());
            case CONTAINS:
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())),
                        "%" + searchCriteria.getValue().toString().toLowerCase() + "%");
            case STARTS_WITH:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()),
                        searchCriteria.getValue().toString() + "%");
            case ENDS_WITH:
                return criteriaBuilder.like(root.get(searchCriteria.getKey()),
                        "%" + searchCriteria.getValue().toString());
            case NAME_OR_DESCRIPTION:
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                                "%" + searchCriteria.getValue().toString().toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                                "%" + searchCriteria.getValue().toString().toLowerCase() + "%"));
            default:
                return null;
        }
    }
}