package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    Role save(Role role);
    void addRoleToUser(Long id, String roleName);
}
