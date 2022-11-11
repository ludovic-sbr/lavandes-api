package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.exceptions.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    Token authenticate(String email, String password) throws AuthenticationException;
}
