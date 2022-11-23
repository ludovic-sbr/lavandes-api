package com.feliiks.gardons.repositories;

import com.feliiks.gardons.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "rest")
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select user from User user where user.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    void deleteById(Long id);

    User save(User user);
}
