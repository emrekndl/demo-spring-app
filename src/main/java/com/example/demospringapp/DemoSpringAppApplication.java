package com.example.demospringapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoSpringAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringAppApplication.class, args);
	}

}
