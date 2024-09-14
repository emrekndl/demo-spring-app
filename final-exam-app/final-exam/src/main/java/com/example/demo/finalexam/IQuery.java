package com.example.demo.finalexam;

import org.springframework.http.ResponseEntity;

/**
 * Represents a query operation that takes an input of type `I` and returns a `ResponseEntity<O>`.
 *
 * @param <I> the type of the input parameter
 * @param <O> the type of the response entity
 */
public interface IQuery<I, O> {
   ResponseEntity<O> execute(I input); 

}
