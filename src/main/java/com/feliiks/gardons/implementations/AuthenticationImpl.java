package com.feliiks.gardons.implementations;

import com.feliiks.gardons.exceptions.AuthenticationException;
import com.feliiks.gardons.exceptions.BusinessException;
import com.feliiks.gardons.services.AuthenticationService;
import com.feliiks.gardons.services.TokenService;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.sqlmodels.TokenModel;
import com.feliiks.gardons.viewmodels.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public TokenModel authenticate(UserEntity user) throws AuthenticationException {
        try {
            if (user.getGoogle_id() != null) {
                Optional<UserEntity> existingUser = userService.findByGoogleId(user.getGoogle_id());

                if (existingUser.isEmpty()) {
                    String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", user.getEmail());

                    throw new BusinessException(errorMessage);
                }

                return tokenService.generateTokenForUser(existingUser.get());
            }

            Optional<UserEntity> existingUser = userService.findByEmail(user.getEmail());

            if (existingUser.isEmpty()) {
                String errorMessage = String.format("L'utilisateur '%s' n'existe pas.", user.getEmail());

                throw new BusinessException(errorMessage);
            }

            boolean encodedPassword = this.passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword());
            if (!encodedPassword) {
                throw new BusinessException("Les mots de passe ne correspondent pas.");
            }

            return tokenService.generateTokenForUser(existingUser.get());
        } catch (BusinessException err) {
            String errorMessage = String.format("Identifiant ou mot de passe incorrect pour l'utilisateur '%s'.", user.getEmail());

            throw new AuthenticationException(errorMessage, err);
        }
    }
}
