package com.feliiks.gardons.implementations;

import com.feliiks.gardons.entities.Token;
import com.feliiks.gardons.entities.User;
import com.feliiks.gardons.exceptions.TokenValidationException;
import com.feliiks.gardons.services.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

import java.util.Date;
import java.sql.Timestamp;

@Service
public class TokenImpl implements TokenService {

    private static final Integer MINUTES = 60;

    private static final Integer HOURS = 60 * MINUTES;

    public static final Integer TOKEN_VALIDITY = 24 * HOURS;

    @Value("${gardons.jwtSecret}")
    private static String jwtSecret = "sqdsd";

    public TokenImpl() { }

    @Override
    public Token createTokenForUser(User user, Integer validity) {
        Date expirationDate = new Date(new Timestamp(System.currentTimeMillis()).getTime() + TOKEN_VALIDITY * 1000);

        String tokenValue = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setIssuer(user.getEmail())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return new Token(user.getId(), user.getEmail(), expirationDate, tokenValue);
    }

    @Override
    public Token generateTokenForUser(User user) {

        if (user == null) return null;

        return createTokenForUser(user, TOKEN_VALIDITY);
    }

    @Override
    public String getUsernameFromToken(String token) throws TokenValidationException {

        if (token == null) throw new TokenValidationException("Token requis.");

        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
