package com.feliiks.gardons.services;

import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.TokenValidationException;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    Token createTokenFromUser(User user, Integer validity);

    Token generateTokenForUser(User user);

    String getUsernameFromToken(String token) throws TokenValidationException;
}
