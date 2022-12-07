package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.UserConverter;
import com.feliiks.gardons.dtos.LoginUserRequest;
import com.feliiks.gardons.dtos.LoginUserResponse;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.models.TokenModel;
import com.feliiks.gardons.services.AuthenticationService;
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
    private final UserConverter userConverter;

    public AuthenticationController(
            AuthenticationService authenticationService,
            UserConverter userConverter
    ) {
        this.authenticationService = authenticationService;
        this.userConverter = userConverter;
    }

    @Operation(summary = "Authenticate a user.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<LoginUserResponse> authenticateUser(@RequestBody LoginUserRequest loginUserRequest) throws AuthenticationException {
        UserEntity user = userConverter.convertToEntity(loginUserRequest);

        TokenModel token = authenticationService.authenticate(user);

        return ResponseEntity.status(200).body(new LoginUserResponse(token.getValue()));
    }
}
