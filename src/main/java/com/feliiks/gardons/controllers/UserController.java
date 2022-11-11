package com.feliiks.gardons.controllers;

import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(200).body(userService.findAll());
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) throws BusinessException {
        return ResponseEntity.status(200).body(userService.findById(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<User> saveNewUser(@RequestBody User newUser) throws BusinessException {
        return ResponseEntity.status(201).body(userService.register(newUser));
    }
}
