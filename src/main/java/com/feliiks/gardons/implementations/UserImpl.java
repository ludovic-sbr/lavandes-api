package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Role;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.services.RoleService;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.viewmodels.PatchUserRequest;
import com.feliiks.gardons.viewmodels.PostUserRequest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserImpl(
            UserRepository userRepository,
            RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.roleService = roleService;
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
    public User register(PostUserRequest registerUserRequest) throws BusinessException {
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

        Optional<Role> role = roleService.findById(1L);
        role.ifPresent(newUser::setRole);

        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public User editUser(PatchUserRequest patchUserRequest) throws BusinessException {
        Optional<User> user = this.findById(patchUserRequest.getId());

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", patchUserRequest.getId());

            throw new BusinessException(errorMessage);
        }

        if (patchUserRequest.getFirstname() != null) {
            user.get().setFirstname(patchUserRequest.getFirstname());
        }

        if (patchUserRequest.getLastname() != null) {
            user.get().setLastname(patchUserRequest.getLastname());
        }

        if (patchUserRequest.getEmail() != null) {
            Optional<User> existingUser = findByEmail(patchUserRequest.getEmail());

            if (existingUser.isPresent()) {
                String errorMessage = String.format("L'adresse email '%s' n'est pas disponible.", patchUserRequest.getEmail());

                throw new BusinessException(errorMessage);
            }

            user.get().setEmail(patchUserRequest.getEmail());
        }

        if (patchUserRequest.getTel() != null) {
            user.get().setTel(patchUserRequest.getTel());
        }

        if (patchUserRequest.getRoleName() != null) {
            Optional<Role> role = roleService.findByName(patchUserRequest.getRoleName());

            if (role.isEmpty()) {
                String errorMessage = String.format("Le rôle '%s' n'existe pas.", patchUserRequest.getRoleName());

                throw new BusinessException(errorMessage);
            }

            user.get().setRole(role.get());
        }

        return userRepository.save(user.get());
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