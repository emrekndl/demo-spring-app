package com.example.demo.finalexam.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.finalexam.ICommand;

@Service
public class CreateNewUserService implements ICommand<CustomUser, String>{

    private final ICustomUserRepository customUserRepository;
    private final PasswordEncoder passwordEncoder;
    
    private static final Logger logger = LoggerFactory.getLogger(CreateNewUserService.class);

    public CreateNewUserService(ICustomUserRepository customUserRepository, PasswordEncoder passwordEncoder) {
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<String> execute(CustomUser user) {
        Optional<CustomUser> existingUser = customUserRepository.findById(user.getUsername());
        if (!existingUser.isPresent()) {
            customUserRepository.save(new CustomUser(user.getUsername(), passwordEncoder.encode(user.getPassword())));
            logger.info(String.format("User %s created successfully", user.getUsername()));
            return ResponseEntity.ok("User created successfully");
        }
        logger.info(String.format("User %s already exists", user.getUsername()));
        return ResponseEntity.badRequest().body("Failure!");
    }

    
}
