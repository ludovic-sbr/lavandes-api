package com.feliiks.gardons.repositories;

import com.feliiks.gardons.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(path = "rest")
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select role from Role role where role.name = :name")
    Optional<Role> findByName(@Param("name") String name);
}
