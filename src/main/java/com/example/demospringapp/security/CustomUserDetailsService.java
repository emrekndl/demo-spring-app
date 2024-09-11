package com.example.demospringapp.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ICustomUserRespository customUserRespository;

    public CustomUserDetailsService(ICustomUserRespository customUserRespository) {
        this.customUserRespository = customUserRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // can add logic to deal with the optional
        CustomUser customUser = customUserRespository.findById(username).get();
        
        // this is where you can add roles and authorities to the user
        // relational mapping to get roles and authorities from another table or same table

        return User
            .withUsername(customUser.getUsername())
            .password(customUser.getPassword())
            .build();
    }

}
