package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.UserAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User findByEmail(String email);
    ResponseEntity<User> register(User user);
}
