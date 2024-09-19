package com.example.demo.finalexam.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomUserRepository extends JpaRepository<CustomUser, String> {

    // CustomUser findByUsername(String username);

}
