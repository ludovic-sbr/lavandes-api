package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.ReservationEntity;
import com.feliiks.gardons.entities.RoleEntity;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.services.RoleService;
import com.feliiks.gardons.services.UserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserImpl implements UserService {
    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    public UserImpl(
            UserRepository userRepository,
            RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.roleService = roleService;
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByGoogleId(String googleId) {
        return userRepository.findByGoogleId(googleId);
    }


    @Override
    public List<ReservationEntity> findUserReservations(Long id) throws BusinessException {
        Optional<UserEntity> user = this.findById(id);

        if (user.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);

            throw new BusinessException(errorMessage);
        }

        return userRepository.findUserReservations(user.get().getId());
    }

    @Override
    public UserEntity register(UserEntity user) throws BusinessException {
        Optional<UserEntity> existingUser = findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            String errorMessage = String.format("L'utilisateur '%s' existe déjà.", user.getEmail());

            throw new BusinessException(errorMessage);
        }

        if (!patternMatches(user.getEmail(), emailRegex)) {
            throw new BusinessException("Adresse email incorrecte.");
        }

        UserEntity newUser = new UserEntity();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword() != null ?
                this.passwordEncoder.encode(user.getPassword())
                : null);
        newUser.setGoogle_id(user.getGoogle_id());

        Optional<RoleEntity> role = roleService.findById(1L);
        role.ifPresent(newUser::setRole);

        newUser.setIs_user_completed(false);

        return userRepository.save(newUser);
    }

    @Override
    public UserEntity editUser(Long id, UserEntity user) throws BusinessException {
        Optional<UserEntity> existingUser = this.findById(id);

        if (existingUser.isEmpty()) {
            String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);

            throw new BusinessException(errorMessage);
        }

        if (user.getFirstname() != null) {
            existingUser.get().setFirstname(user.getFirstname());
        }

        if (user.getLastname() != null) {
            existingUser.get().setLastname(user.getLastname());
        }

        if (user.getEmail() != null) {
            Optional<UserEntity> userExists = findByEmail(user.getEmail());

            if (!patternMatches(user.getEmail(), emailRegex)) {
                throw new BusinessException("Adresse email incorrecte.");
            }

            if (userExists.isPresent()) {
                String errorMessage = String.format("L'adresse email '%s' n'est pas disponible.", user.getEmail());

                throw new BusinessException(errorMessage);
            }

            existingUser.get().setEmail(user.getEmail());
        }

        if (user.getRole() != null && user.getRole().getName() != null) {
            Optional<RoleEntity> role = roleService.findByName(user.getRole().getName());

            if (role.isEmpty()) {
                String errorMessage = String.format("Le rôle '%s' n'existe pas.", user.getRole().getName());

                throw new BusinessException(errorMessage);
            }

            existingUser.get().setRole(role.get());
        }

        existingUser.get().setIs_user_completed(user.getIs_user_completed());

        return userRepository.save(existingUser.get());
    }

    @Override
    public Optional<UserEntity> deleteById(Long id) {
        try {
            Optional<UserEntity> user = userRepository.findById(id);

            userRepository.deleteById(id);

            return user;
        } catch (EmptyResultDataAccessException err) {
            return Optional.empty();
        }
    }

    public static boolean patternMatches(String string, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(string)
                .matches();
    }
}