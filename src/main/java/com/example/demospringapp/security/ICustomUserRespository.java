package com.example.demospringapp.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomUserRespository extends JpaRepository<CustomUser, String> {

}
