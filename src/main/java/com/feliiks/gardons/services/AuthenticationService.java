package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.UserEntity;
import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.models.TokenModel;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    TokenModel authenticate(UserEntity user) throws AuthenticationException;
}
