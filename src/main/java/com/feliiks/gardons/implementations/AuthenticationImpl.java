package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.AuthenticationService;
import com.feliiks.gardons.services.TokenService;
import com.feliiks.gardons.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationImpl implements AuthenticationService {
    public final UserService userService;
    public final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationImpl(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Token authenticate(String email, String password) throws AuthenticationException {
        try {
            User user = userService.findByEmail(email);

            // check du mot de passe

            return tokenService.generateTokenForUser(user);
        } catch (BusinessException err) {
            String errorMessage = String.format("Identifiant ou mot de passe incorrect pour l'utilisateur '%s'.", email);

            throw new AuthenticationException(errorMessage);
        }
    }
}
