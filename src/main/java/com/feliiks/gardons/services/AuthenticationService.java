package com.feliiks.gardons.services;

import com.feliiks.gardons.sqlmodels.TokenModel;
import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.viewmodels.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    TokenModel authenticate(UserEntity user) throws AuthenticationException;
}
