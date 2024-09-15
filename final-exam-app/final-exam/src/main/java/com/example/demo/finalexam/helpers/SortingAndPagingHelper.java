package com.example.demo.finalexam.helpers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortingAndPagingHelper {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 1000;
    private static final String DEFAULT_DIRECTION = "ASC";

    public Pageable createPageable(Integer page, Integer size, String sortBy, String direction) {
        int pageSize = (size != null) ? size : DEFAULT_SIZE;
        page = (page != null) ? page : DEFAULT_PAGE;
        direction = (direction != null) ? direction : DEFAULT_DIRECTION;
        pageSize = Math.min(pageSize, MAX_SIZE); 

        Sort sort = Sort.unsorted();
        
        if (sortBy != null && direction != null) {
            Sort.Direction sortDirection = Sort.Direction.fromString(direction);
            sort = Sort.by(sortDirection, sortBy);
        }

        if (sortBy != null) {
            return PageRequest.of(page, pageSize, sort);
        }

        return PageRequest.of(page, pageSize);
    }
}
