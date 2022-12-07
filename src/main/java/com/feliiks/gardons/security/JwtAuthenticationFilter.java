package com.feliiks.gardons.security;

import com.feliiks.gardons.services.TokenService;
import com.feliiks.gardons.services.UserService;
import com.feliiks.gardons.viewmodels.UserEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String AUTHENTICATION_TYPE = "Bearer";
    private final TokenService tokenService;
    private final UserService userService;


    public JwtAuthenticationFilter(
            TokenService tokenService,
            UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader(AUTHENTICATION_HEADER);

        if (header == null || !header.startsWith(AUTHENTICATION_TYPE)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(AUTHENTICATION_TYPE, "");

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getCredentials() != null
                && SecurityContextHolder.getContext().getAuthentication().getCredentials().equals(token)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String username = tokenService.getUsernameFromToken(token);

            Optional<UserEntity> user = userService.findByEmail(username);

            if (user.isEmpty()) {
                chain.doFilter(request, response);
                return;
            }

            UserEntity userEntity = user.get();

            List<GrantedAuthority> authorities = new ArrayList<>();

            if (userEntity.getRole() != null) {
                authorities.add(new SimpleGrantedAuthority(String.format("ROLE_%s", userEntity.getRole().getName())));
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userEntity, token, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            logger.error("L'authentification a échoué.", e);
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
