package com.example.demo.finalexam.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   private final ICustomUserRepository repository; 

   public CustomUserDetailsService(ICustomUserRepository repository) {
       this.repository = repository;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       CustomUser user = repository.findById(username)
           .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .build();
   }
}
