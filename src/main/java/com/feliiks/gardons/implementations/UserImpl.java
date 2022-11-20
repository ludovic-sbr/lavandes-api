package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Role;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.RoleRepository;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.viewmodels.PatchUserRequest;
import com.feliiks.gardons.viewmodels.RegisterUserRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserImpl(
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User register(RegisterUserRequest registerUserRequest) throws BusinessException {
        Optional<User> existingUser = findByEmail(registerUserRequest.getEmail());

        if (existingUser.isPresent()) {
            String errorMessage = String.format("L'utilisateur '%s' existe déjà.", registerUserRequest.getEmail());

            throw new BusinessException(errorMessage);
        }

        User newUser = new User();
        newUser.setFirstname(registerUserRequest.getFirstname());
        newUser.setLastname(registerUserRequest.getLastname());
        newUser.setEmail(registerUserRequest.getEmail());
        newUser.setTel(registerUserRequest.getTel());

        newUser.setPassword(this.passwordEncoder.encode(registerUserRequest.getPassword()));

        Optional<Role> role = roleRepository.findById(1L);
        role.ifPresent(newUser::setRole);

        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public User editUser(PatchUserRequest patchUserRequest) throws BusinessException {
        Optional<User> user = userRepository.findById(22L);
        if (user.isEmpty()) return null;
        return user.get();
    }

    @Override
    public Optional<User> deleteById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);

            userRepository.deleteById(id);

            return user;
        } catch (EmptyResultDataAccessException err) {
            return Optional.empty();
        }
    }
}