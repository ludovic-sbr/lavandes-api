package com.feliiks.gardons.services;

import com.feliiks.gardons.sqlmodels.TokenModel;
import com.feliiks.gardons.exceptions.TokenValidationException;
import com.feliiks.gardons.viewmodels.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    TokenModel createTokenFromUser(UserEntity user, Integer validity);

    TokenModel generateTokenForUser(UserEntity user);

    String getUsernameFromToken(String token) throws TokenValidationException;
}
