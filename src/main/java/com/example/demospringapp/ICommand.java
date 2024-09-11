package com.example.demospringapp;

import org.springframework.http.ResponseEntity;

public interface ICommand<I, O> {

    ResponseEntity<O> execute(I input);    
}
