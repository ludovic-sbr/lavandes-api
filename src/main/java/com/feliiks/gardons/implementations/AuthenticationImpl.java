package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.AuthenticationService;
import com.feliiks.gardons.services.TokenService;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.viewmodels.LoginUserRequest;
import com.feliiks.gardons.viewmodels.LoginUserResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
    public Token authenticate(LoginUserRequest loginUserRequest) throws AuthenticationException {
        try {
            Optional<User> user = userService.findByEmail(loginUserRequest.getEmail());

            if (user.isEmpty()) {
                String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", loginUserRequest.getEmail());

                throw new BusinessException(errorMessage);
            }

            boolean encodedPassword = this.passwordEncoder.matches(loginUserRequest.getPassword(), user.get().getPassword());
            if (!encodedPassword) {
                String errorMessage = String.format("Identifiant ou mot de passe incorrect pour l'utilisateur '%s'.", loginUserRequest.getEmail());

                throw new BusinessException(errorMessage);
            }

            return tokenService.generateTokenForUser(user.get());
        } catch (BusinessException err) {
            String errorMessage = String.format("Identifiant ou mot de passe incorrect pour l'utilisateur '%s'.", loginUserRequest.getEmail());

            throw new AuthenticationException(errorMessage, err);
        }
    }
}
