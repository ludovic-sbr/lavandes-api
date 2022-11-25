package com.feliiks.gardons.controllers;

import com.feliiks.gardons.entities.Reservation;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.viewmodels.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "User")
@RestController
@RequestMapping("/user")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Operation(summary = "List all users.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<GetUsersResponse> getAllUsers() {
        List<User> users = userService.findAll();

        return ResponseEntity.status(200).body(new GetUsersResponse(users));
    }

    @Operation(summary = "Get the current user.")
    @GetMapping(path = "/me", produces = "application/json")
    public ResponseEntity<GetUserResponse> getMyself() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        User mappedUser = new ModelMapper().map(user, User.class);

        return ResponseEntity.status(200).body(new GetUserResponse(mappedUser));
    }

    @Operation(summary = "List all reservations of current user.")
    @GetMapping(path = "/me/reservation", produces = "application/json")
    public ResponseEntity<GetReservationsResponse> getMyReservations() throws BusinessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        User mappedUser = new ModelMapper().map(user, User.class);

        List<Reservation> userReservations = userService.findUserReservations(mappedUser.getId());

        if (userReservations.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'a aucune réservation.", mappedUser.getId());
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetReservationsResponse(userReservations));
    }

    @Operation(summary = "Get user by id.")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable("id") Long id) throws BusinessException {
        Optional<User> user = userService.findById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetUserResponse(user.get()));
    }

    @Operation(summary = "List all reservations of specific user.")
    @GetMapping(path = "/{id}/reservation", produces = "application/json")
    public ResponseEntity<GetReservationsResponse> getUserReservations(@PathVariable("id") Long id) throws BusinessException {
        List<Reservation> userReservations = userService.findUserReservations(id);

        if (userReservations.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'a aucune réservation.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new GetReservationsResponse(userReservations));
    }

    @Operation(summary = "Create a new user.")
    @PostMapping(produces = "application/json")
    public ResponseEntity<PostUserResponse> saveNewUser(@RequestBody PostUserRequest postUserRequest) throws BusinessException {
        User user = userService.register(postUserRequest);

        return ResponseEntity.status(201).body(new PostUserResponse(user));
    }

    @Operation(summary = "Partial update a specific user.")
    @PatchMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PatchUserResponse> editUser(@PathVariable("id") Long id, @RequestBody PatchUserRequest patchUserRequest) throws BusinessException {
        User user = userService.editUser(id, patchUserRequest);

        return ResponseEntity.status(200).body(new PatchUserResponse(user));
    }

    @Operation(summary = "Partial update the current user.")
    @PatchMapping(path = "/me", produces = "application/json")
    public ResponseEntity<PatchUserResponse> editMyself(@RequestBody PatchUserRequest patchUserRequest) throws BusinessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();
        User mappedUser = new ModelMapper().map(user, User.class);

        User patchedUser = userService.editUser(mappedUser.getId(), patchUserRequest);

        return ResponseEntity.status(200).body(new PatchUserResponse(patchedUser));
    }

    @Operation(summary = "Delete a specific user.")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable("id") Long id) throws BusinessException {
        Optional<User> user = userService.deleteById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);
            throw new BusinessException(errorMessage);
        }

        return ResponseEntity.status(200).body(new DeleteUserResponse(user.get()));
    }
}
