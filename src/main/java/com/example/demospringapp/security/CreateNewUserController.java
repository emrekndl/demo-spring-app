package com.example.demospringapp.security;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateNewUserController {

    private final PasswordEncoder encoder;

    private final ICustomUserRespository customUserRespository;

    public CreateNewUserController(PasswordEncoder encoder, ICustomUserRespository customUserRespository) {
        this.encoder = encoder;
        this.customUserRespository = customUserRespository;
    }

    @PostMapping("/createnewuser")
    public ResponseEntity<String> createNewUser(@RequestBody CustomUser user) {
        // this should go in a service class
        // need better error handling with custom exceptions

        Optional<CustomUser> optionalUser = customUserRespository.findById(user.getUsername()); 

        if (!optionalUser.isPresent()) {
            customUserRespository.save(new CustomUser(user.getUsername(), encoder.encode(user.getPassword())));
            return ResponseEntity.ok("User created successfully");
        }
        
        return ResponseEntity.badRequest().body("Failure: User already exists");
    }
}
