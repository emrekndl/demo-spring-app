package com.example.demospringapp.security;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class SecurityController {
    
    @GetMapping("/open")
    public String open() {
        return "OPEN"; 
    }
    
    @GetMapping("/closed")
    public String closed() {
        return "CLOSED";
    }
    
    @PreAuthorize("hasRole('superuser')")
    @GetMapping("/special")
    public String special() {
        return "SPECIAL";
    }

    // SpEL spring expression language is used for authorization checks
    @PreAuthorize("hasRole('basicuser') or hasRole('superuser')")
    @GetMapping("/basic")
    public String basic() {
        return "BASIC";
    }
    
}
