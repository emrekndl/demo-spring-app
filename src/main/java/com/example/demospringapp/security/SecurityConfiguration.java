package com.example.demospringapp.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demospringapp.security.jwt.JwtAuthenticationFilter;

import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.core.userdetails.User;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    
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

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    //     UserDetails admin = User
    //             .withUsername("admin")
    //             .authorities("BASIC", "SPECIAL")
    //             .roles("superuser") // enable method security roles
    //             .password(encoder.encode("1")) // spring boot wont let you use raw text for password, PasswordEncoder does that for you
    //             .build();
    //     
    //     UserDetails user = User
    //             .withUsername("user")
    //             .authorities("BASIC")
    //             .roles("basicuser")
    //             .password(encoder.encode("2"))
    //             .build();
    //     
    //     return new InMemoryUserDetailsManager(admin, user);
    // }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity
            // .csrf(csrf -> csrf.disable())
            // disable csrf, allows for POST, PUT, PATCH, DELETE requests with authentication
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> {
                // authorize.requestMatchers("/open").permitAll();
                // authorize.requestMatchers("/closed").authenticated();
                // authorize.requestMatchers(HttpMethod.POST, "/product").authenticated();
                // 
                // authorize.requestMatchers(HttpMethod.GET, "/special").hasAuthority("SPECIAL");
                // authorize.requestMatchers(HttpMethod.GET, "/basic").hasAnyAuthority("BASIC", "SPECIAL");
                // have to let user create a new without valid credentials
            
                authorize.anyRequest().permitAll();
                // authorize.requestMatchers("/createnewuser").permitAll();
                // authorize.requestMatchers("/login").permitAll();
                
                // must be at the bottom
                // authorize.anyRequest().authenticated(); // all other requests require authentication - enble method security
            }) 
            // .httpBasic(Customizer.withDefaults()) // for HTTP Basic authentication
            
            // .addFilterBefore(new BasicAuthenticationFilter(authenticationManager(httpSecurity)),
            // UsernamePasswordAuthenticationFilter.class)
            
            // jwt authentication filter 
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .build();
    }
    
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

}
