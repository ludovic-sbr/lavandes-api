package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.viewmodels.LoginUserRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    Token authenticate(LoginUserRequest loginUserRequest) throws AuthenticationException;
}
