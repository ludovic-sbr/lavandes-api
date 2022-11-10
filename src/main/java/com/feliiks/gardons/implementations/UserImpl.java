package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
public class UserImpl implements UserService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user;

        try {
            user = userRepository.findById(id);
            if (user.isEmpty()) throw new ResponseStatusException(NOT_FOUND);
        } catch (ResponseStatusException err) {
            String errorMessage = String.format("Une erreur est survenue lors de la récupération de l'utilisateur '%s'.", id);
            throw new ResponseStatusException(NOT_FOUND, errorMessage);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public ResponseEntity<User> register(User user) {
        try {
            User existingUser = findByEmail(user.getEmail());

            if (existingUser != null) {
                throw new ResponseStatusException(BAD_REQUEST);
            }

            user.setPassword(this.passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);

            return ResponseEntity.status(201).body(user);
        } catch (ResponseStatusException err) {
            return ResponseEntity.badRequest().build();
        }
    }
}