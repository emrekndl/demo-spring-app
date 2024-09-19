package com.example.demo.finalexam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.finalexam.security.jwt.JwtAuthenticationFilter;

/* 
    * SPRING SECURITY FLOW
    * Security Filter Chain
    *      if Basic Auth is in the header -> Basic Auth Filter Triggered
    *          Basic Auth Filter calls -> Authentication Manager
    *              Authentication Manager calls -> User Details Service
    *                  User Details Service calls -> loadUserByUsername
    *                      This method validates the credentials
    *                          if valid -> added to Security Context
    *                          if invalid -> deny request
*/

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // allows for post, put, delete mapping with authenication
                .authorizeHttpRequests(authorize -> {
                    authorize.requestMatchers("/login").permitAll();
                    authorize.requestMatchers("/api/createnewuser").permitAll();
                    authorize.requestMatchers(HttpMethod.GET, "/api/products").permitAll();

                    authorize.anyRequest().authenticated();
                })
                // .addFilterBefore(
                //         new BasicAuthenticationFilter(authenticationManager(http)),
                //         UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(
                    jwtAuthenticationFilter(),
                    UsernamePasswordAuthenticationFilter.class
                )             
                .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    
}
