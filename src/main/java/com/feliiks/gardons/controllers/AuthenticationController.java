package com.feliiks.gardons.controllers;

import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.services.AuthenticationService;
import com.feliiks.gardons.viewmodels.LoginUserRequest;
import com.feliiks.gardons.viewmodels.LoginUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
    public final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Authenticate a user.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<LoginUserResponse> authenticateUser(@RequestBody LoginUserRequest loginUserRequest) throws AuthenticationException {
        Token token = authenticationService.authenticate(loginUserRequest);

        return ResponseEntity.status(200).body(new LoginUserResponse(token.getValue()));
    }
}
