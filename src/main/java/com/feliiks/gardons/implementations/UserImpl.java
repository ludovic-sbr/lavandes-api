package com.feliiks.gardons.implementations;

import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.viewmodels.ReservationEntity;
import com.feliiks.gardons.viewmodels.RoleEntity;
import com.feliiks.gardons.viewmodels.UserEntity;
import com.feliiks.gardons.services.RoleService;
import com.feliiks.gardons.services.UserService;
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

        UserEntity newUser = new UserEntity();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setTel(user.getTel());
        newUser.setPassword(user.getPassword() != null ?
                this.passwordEncoder.encode(user.getPassword())
                : null);
        newUser.setGoogle_id(user.getGoogle_id());

        Optional<RoleEntity> role = roleService.findById(1L);
        role.ifPresent(newUser::setRole);

        userRepository.save(newUser);

        return newUser;
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

            if (userExists.isPresent()) {
                String errorMessage = String.format("L'adresse email '%s' n'est pas disponible.", user.getEmail());

                throw new BusinessException(errorMessage);
            }

            existingUser.get().setEmail(user.getEmail());
        }

        if (user.getTel() != null) {
            existingUser.get().setTel(user.getTel());
        }

        if (user.getRole().getName() != null) {
            Optional<RoleEntity> role = roleService.findByName(user.getRole().getName());

            if (role.isEmpty()) {
                String errorMessage = String.format("Le rôle '%s' n'existe pas.", user.getRole().getName());

                throw new BusinessException(errorMessage);
            }

            existingUser.get().setRole(role.get());
        }

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
}