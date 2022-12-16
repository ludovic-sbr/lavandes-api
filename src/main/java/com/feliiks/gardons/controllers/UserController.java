package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.UserConverter;
import com.feliiks.gardons.dtos.*;
import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "User")
@RestController
@RequestMapping("/user")
public class UserController {
    public final UserService userService;
    private final UserConverter userConverter;

    public UserController(
            UserService userService,
            UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @Operation(summary = "List all users.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<GetUsersResponse> getAllUsers() {
        List<UserEntity> users = userService.findAll();

        return ResponseEntity.status(200).body(new GetUsersResponse(users));
    }

    @Operation(summary = "Get the current user.")
    @GetMapping(path = "/me", produces = "application/json")
    public ResponseEntity<GetUserResponse> getMyself() {
        UserEntity user = userConverter.getLoggedUser();

        return ResponseEntity.status(200).body(new GetUserResponse(user));
    }

    @Operation(summary = "List all reservations of current user.")
    @GetMapping(path = "/reservation", produces = "application/json")
    public ResponseEntity<GetReservationsResponse> getMyReservations() throws BusinessException {
        UserEntity user = userConverter.getLoggedUser();

        List<ReservationEntity> userReservations = userService.findUserReservations(user.getId());

        if (userReservations.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'a aucune réservation.", user.getId());
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetReservationsResponse(userReservations));
    }

    @Operation(summary = "Get user by id.")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable("id") Long id) throws BusinessException {
        Optional<UserEntity> user = userService.findById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetUserResponse(user.get()));
    }

    @Operation(summary = "List all reservations of specific user.")
    @GetMapping(path = "/{id}/reservation", produces = "application/json")
    public ResponseEntity<GetReservationsResponse> getUserReservations(@PathVariable("id") Long id) throws BusinessException {
        List<ReservationEntity> userReservations = userService.findUserReservations(id);

        if (userReservations.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'a aucune réservation.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetReservationsResponse(userReservations));
    }

    @Operation(summary = "Create a new user.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<PostUserResponse> saveNewUser(@RequestBody PostUserRequest postUserRequest) throws BusinessException {
        UserEntity user = userConverter.convertToEntity(postUserRequest);

        if (postUserRequest.getPassword() != null && !Objects.equals(postUserRequest.getPassword(), postUserRequest.getRepeat_password())) {
            throw new BusinessException("Les mots de passe ne correspondent pas.");
        }

        UserEntity savedUser = userService.register(user);

        return ResponseEntity.status(201).body(new PostUserResponse(savedUser));
    }

    @Operation(summary = "Partial update a specific user.")
    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PatchUserResponse> editUser(@PathVariable("id") Long id, @RequestBody PatchUserRequest patchUserRequest) throws BusinessException {
        UserEntity user = userConverter.convertToEntity(patchUserRequest);
        UserEntity userToPatch = userService.editUser(id, user);

        return ResponseEntity.status(200).body(new PatchUserResponse(userToPatch));
    }

    @Operation(summary = "Partial update the current user.")
    @PatchMapping(path = "/me", produces = "application/json")
    public ResponseEntity<PatchUserResponse> editMyself(@RequestBody PatchUserRequest patchUserRequest) throws BusinessException {
        UserEntity user = userConverter.getLoggedUser();
        UserEntity patch = userConverter.convertToEntity(patchUserRequest);

        UserEntity patchedUser = userService.editUser(user.getId(), patch);

        return ResponseEntity.status(200).body(new PatchUserResponse(patchedUser));
    }

    @Operation(summary = "Delete a specific user.")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable("id") Long id) throws BusinessException {
        Optional<UserEntity> user = userService.deleteById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new DeleteUserResponse(user.get()));
    }
}
