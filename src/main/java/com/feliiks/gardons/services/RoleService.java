package com.feliiks.gardons.services;

import com.feliiks.gardons.viewmodels.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {
    List<RoleEntity> findAll();

    Optional<RoleEntity> findById(Long id);

    Optional<RoleEntity> findByName(String name);

    RoleEntity save(RoleEntity role);

    void addRoleToUser(Long id, String roleName);
}
