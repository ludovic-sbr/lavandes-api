package com.feliiks.gardons.security;

import com.feliiks.gardons.converters.UserConverter;
import com.feliiks.gardons.entities.UserEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    public UserConverter userConverter;

    public CustomAccessDeniedHandler(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        UserEntity user = userConverter.getLoggedUser();

        response.sendError(HttpServletResponse.SC_FORBIDDEN, String.format("Accès refusé pour l'utilisateur %s à la ressource demandée.", user.getEmail()));
    }
}
