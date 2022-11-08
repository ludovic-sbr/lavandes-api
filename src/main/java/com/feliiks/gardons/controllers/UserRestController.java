package com.feliiks.gardons.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @GetMapping(produces = "application/json")
    public ResponseEntity<String> getUser() {
        return ResponseEntity.status(200).body("test");
    }

    @DeleteMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<String> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body("test id" + id);
    }
}
