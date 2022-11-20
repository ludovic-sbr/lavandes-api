package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Reservation;
import com.feliiks.gardons.entities.Role;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.repositories.RoleRepository;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.services.RoleService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class RoleImpl implements RoleService {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;

    public RoleImpl(
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        Optional<Role> role;

        try {
            role = roleRepository.findById(id);
            if (role.isEmpty()) throw new ResponseStatusException(NOT_FOUND);
        } catch (ResponseStatusException err) {
            String errorMessage = String.format("Une erreur est survenue lors de la récupération du rôle '%s'.", id);
            throw new ResponseStatusException(NOT_FOUND, errorMessage);
        }

        return role;
    }

    @Override
    public Optional<Role> findByName(String name) {
        Optional<Role> role;

        try {
            role = roleRepository.findByName(name);
            if (role.isEmpty()) throw new ResponseStatusException(NOT_FOUND);
        } catch (ResponseStatusException err) {
            String errorMessage = String.format("Une erreur est survenue lors de la récupération du rôle '%s'.", name);
            throw new ResponseStatusException(NOT_FOUND, errorMessage);
        }

        return role;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(Long id, String roleName) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        user.setRole(role);
    }
}
