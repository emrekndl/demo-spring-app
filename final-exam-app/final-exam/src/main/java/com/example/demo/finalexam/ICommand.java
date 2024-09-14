package com.example.demo.finalexam;

import org.springframework.http.ResponseEntity;

/**
 * Represents a command that can be executed with a given input and returns a response entity.
 *
 * @param <I> the type of the input parameter for the command
 * @param <O> the type of the output parameter for the command
 */
public interface ICommand<I, O> {
    ResponseEntity<O> execute(I input);

}


