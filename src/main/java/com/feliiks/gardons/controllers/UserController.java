package com.feliiks.gardons.controllers;

import com.feliiks.gardons.converters.UserConverter;
import com.feliiks.gardons.dtos.GetReservationsResponse;
import com.feliiks.gardons.dtos.GetUsersResponse;
import com.feliiks.gardons.dtos.UserRequest;
import com.feliiks.gardons.dtos.UserResponse;
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
@RequestMapping(value = "/user", produces = "application/json; charset=utf-8")
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
    @GetMapping()
    public ResponseEntity<GetUsersResponse> getAllUsers() {
        List<UserEntity> users = userService.findAll();

        return ResponseEntity.status(200).body(new GetUsersResponse(users));
    }

    @Operation(summary = "Get the current user.")
    @GetMapping(path = "/me")
    public ResponseEntity<UserResponse> getMyself() {
        UserEntity user = userConverter.getLoggedUser();

        return ResponseEntity.status(200).body(new UserResponse(user));
    }

    @Operation(summary = "List all reservations of current user.")
    @GetMapping(path = "/reservation")
    public ResponseEntity<GetReservationsResponse> getMyReservations() throws BusinessException {
        UserEntity user = userConverter.getLoggedUser();

        List<ReservationEntity> userReservations = userService.findUserReservations(user.getId());

        return ResponseEntity.status(200).body(new GetReservationsResponse(userReservations));
    }

    @Operation(summary = "Get user by id.")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) throws BusinessException {
        Optional<UserEntity> user = userService.findById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new UserResponse(user.get()));
    }

    @Operation(summary = "List all reservations of specific user.")
    @GetMapping(path = "/{id}/reservation")
    public ResponseEntity<GetReservationsResponse> getUserReservations(@PathVariable("id") Long id) throws BusinessException {
        List<ReservationEntity> userReservations = userService.findUserReservations(id);

        if (userReservations.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'a aucune réservation.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetReservationsResponse(userReservations));
    }

    @Operation(summary = "Create a new user.")
    @PostMapping()
    public ResponseEntity<UserResponse> saveNewUser(@RequestBody UserRequest userRequest) throws BusinessException {
        UserEntity user = userConverter.convertToEntity(userRequest);

        if (userRequest.getPassword() != null && !Objects.equals(userRequest.getPassword(), userRequest.getRepeat_password())) {
            throw new BusinessException("Les mots de passe ne correspondent pas.");
        }

        UserEntity savedUser = userService.register(user);

        return ResponseEntity.status(201).body(new UserResponse(savedUser));
    }

    @Operation(summary = "Partial update a specific user.")
    @PatchMapping(path = "/{id}")
    public ResponseEntity<UserResponse> editUser(@PathVariable("id") Long id, @RequestBody UserRequest userRequest) throws BusinessException {
        UserEntity user = userConverter.convertToEntity(userRequest);

        if (userRequest.getPassword() != null && !Objects.equals(userRequest.getPassword(), userRequest.getRepeat_password())) {
            throw new BusinessException("Les mots de passe ne correspondent pas.");
        }

        UserEntity userToPatch = userService.editUser(id, user);

        return ResponseEntity.status(200).body(new UserResponse(userToPatch));
    }

    @Operation(summary = "Delete a specific user.")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable("id") Long id) throws BusinessException {
        Optional<UserEntity> user = userService.deleteById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new UserResponse(user.get()));
    }
}
