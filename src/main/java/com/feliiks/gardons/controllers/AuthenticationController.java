package com.feliiks.gardons.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
    public final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Token> authenticateUser(@RequestBody ObjectNode credentials) throws AuthenticationException {
        String username = credentials.get("username").asText();
        String password = credentials.get("password").asText();

        return ResponseEntity.status(200).body(authenticationService.authenticate(username, password));
    }
}
