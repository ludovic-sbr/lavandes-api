package com.feliiks.gardons.repositories.jpa;

import com.feliiks.gardons.sqlmodels.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(path = "rest")
public interface RoleJpaRepository extends JpaRepository<RoleModel, Long> {
    @Query("select role from RoleModel role where role.name = :name")
    Optional<RoleModel> findByName(@Param("name") String name);
}
