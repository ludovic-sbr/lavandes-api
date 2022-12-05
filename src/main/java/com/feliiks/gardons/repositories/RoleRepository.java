package com.feliiks.gardons.repositories;

import com.feliiks.gardons.converters.RoleConverter;
import com.feliiks.gardons.repositories.jpa.RoleJpaRepository;
import com.feliiks.gardons.sqlmodels.RoleModel;
import com.feliiks.gardons.viewmodels.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleRepository {
    private final RoleJpaRepository roleJpaRepository;
    private final RoleConverter roleConverter;

    public RoleRepository(
            RoleJpaRepository roleJpaRepository,
            RoleConverter roleConverter) {
        this.roleJpaRepository = roleJpaRepository;
        this.roleConverter = roleConverter;
    }

    public List<RoleEntity> findAll() {
        List<RoleModel> allRoles = roleJpaRepository.findAll();

        return allRoles.stream().map(roleConverter::convertToEntity).toList();
    }

    public Optional<RoleEntity> findById(Long id) {
        Optional<RoleModel> role = roleJpaRepository.findById(id);

        if (role.isEmpty()) return Optional.empty();

        return Optional.of(roleConverter.convertToEntity(role.get()));
    }

    public Optional<RoleEntity> findByName(String name) {
        Optional<RoleModel> role = roleJpaRepository.findByName(name);

        if (role.isEmpty()) return Optional.empty();

        return Optional.of(roleConverter.convertToEntity(role.get()));
    }

    public RoleEntity save(RoleEntity role) {
        RoleModel mappedRole = roleConverter.convertToModel(role);

        RoleModel savedRole = roleJpaRepository.save(mappedRole);

        return roleConverter.convertToEntity(savedRole);
    }
}
