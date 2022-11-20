package com.feliiks.gardons.controllers;

import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.viewmodels.*;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GetUsersResponse> getAllUsers() {
        List<User> users = userService.findAll();

        return ResponseEntity.status(200).body(new GetUsersResponse(users));
    }

    @GetMapping(path="/me", produces = "application/json")
    public ResponseEntity<GetUserResponse> getMyself() throws BusinessException {
        Optional<User> user = userService.findById(26L);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", 26L);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetUserResponse(user.get()));
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable("id") Long id) throws BusinessException {
        Optional<User> user = userService.findById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetUserResponse(user.get()));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<RegisterUserResponse> saveNewUser(@RequestBody RegisterUserRequest registerUserRequest) throws BusinessException {
        User user = userService.register(registerUserRequest);

        return ResponseEntity.status(201).body(new RegisterUserResponse(user));
    }

    @PatchMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<PatchUserResponse> editUser(@RequestBody PatchUserRequest patchUserRequest) throws BusinessException {
        User user = userService.editUser(patchUserRequest);

        return ResponseEntity.status(200).body(new PatchUserResponse(user));
    }

    @DeleteMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable("id") Long id) throws BusinessException {
        Optional<User> user = userService.deleteById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new DeleteUserResponse(user.get()));
    }
}
