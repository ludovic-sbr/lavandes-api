package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Role;
import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.repositories.RoleRepository;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.services.UserService;
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
    public User findById(Long id) throws BusinessException {
        String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", id);

        return userRepository.findById(id).orElseThrow(() -> new BusinessException(errorMessage));
    }

    @Override
    public User findByEmail(String email) throws BusinessException {
        String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", email);

        return userRepository.findByEmail(email).orElseThrow(() -> new BusinessException(errorMessage));
    }

    @Override
    public User register(User user) throws BusinessException {
        User existingUser = findByEmail(user.getEmail());

        if (existingUser != null) {
            String errorMessage = String.format("L'utilisateur '%s' existe déjà.", user.getEmail());

            throw new BusinessException(errorMessage);
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        Optional<Role> role = roleRepository.findById(1L);
        role.ifPresent(user::setRole);

        userRepository.save(user);

        return user;
    }
}