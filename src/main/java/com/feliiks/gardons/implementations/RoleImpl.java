package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.RoleEntity;
import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.repositories.RoleRepository;
import com.feliiks.gardons.repositories.UserRepository;
import com.feliiks.gardons.services.RoleService;
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
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {

        return roleRepository.findById(id);
    }

    @Override
    public Optional<RoleEntity> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public RoleEntity save(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(Long id, String roleName) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        RoleEntity role = roleRepository.findByName(roleName).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        user.setRole(role);
    }
}
