package com.sodium.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.sodium.api.models.DBUser;
import com.sodium.api.repositories.DBUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class RegisterController {

    @Autowired
    private DBUserRepository dbUserRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody DBUser user) throws Exception {
        if (dbUserRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already in use");
        }
        System.out.println("Registering user: " + user.getUsername());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole("USER");

        dbUserRepository.save(user);
        return ResponseEntity.ok("Registered new user " + user.getUsername() + " successfully");
    }

}
