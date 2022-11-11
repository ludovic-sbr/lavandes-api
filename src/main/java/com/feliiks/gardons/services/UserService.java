package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAll();
    User findById(Long id) throws BusinessException;
    User findByEmail(String email) throws BusinessException;
    User register(User user) throws BusinessException;
}
