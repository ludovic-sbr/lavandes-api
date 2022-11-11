package com.feliiks.gardons.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.impl.DefaultJwtParser;
import io.jsonwebtoken.Claims;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    public JwtAuthenticationFilter(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader(jwtConfig.getHeader());

        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(jwtConfig.getPrefix(), "");

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().getCredentials() != null
                && SecurityContextHolder.getContext().getAuthentication().getCredentials().equals(token)) {
            chain.doFilter(request, response);
            return;
        }

        try {

            int i = token.lastIndexOf('.');
            String withoutSignature = token.substring(0, i+1);

            DefaultJwtParser parser = new DefaultJwtParser();
            Jwt<?, ?> jwt = parser.parse(withoutSignature);
            Claims claims = (Claims) jwt.getBody();

            List<GrantedAuthority> authorities = new ArrayList<>();

            AuthenticationUser user = getUserFromClaims(claims);

            if (user == null) {
                chain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, token, authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            logger.error("Error occurred while authenticated via JWT", e);
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    private AuthenticationUser getUserFromClaims(Claims claims) {

        String cognitoUsername = claims.get("cognito:username", String.class);
        String role = claims.get("custom:role", String.class);

        if (cognitoUsername == null || role == null) {
            return null;
        }
        boolean isPro = role.equals("pro");
        return new AuthenticationUser(isPro, cognitoUsername);
    }

}
