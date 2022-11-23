package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.viewmodels.PatchUserRequest;
import com.feliiks.gardons.viewmodels.PostUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User register(PostUserRequest registerUserRequest) throws BusinessException;

    User editUser(PatchUserRequest patchUserRequest) throws BusinessException;

    Optional<User> deleteById(Long id);
}
