package com.feliiks.gardons.controllers;

import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<User> saveNewUser(@RequestBody User newUser) {
        return userService.register(newUser);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public Optional<User> getUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }
}
