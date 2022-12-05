package com.feliiks.gardons.implementations;

import com.feliiks.gardons.sqlmodels.TokenModel;
import com.feliiks.gardons.exceptions.TokenValidationException;
import com.feliiks.gardons.viewmodels.UserEntity;
import com.feliiks.gardons.services.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class TokenImpl implements TokenService {

    private static final Integer MINUTES = 60;

    private static final Integer HOURS = 60 * MINUTES;

    public static final Integer TOKEN_VALIDITY = 24 * HOURS;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtIssuer}")
    private String issuer;

    public TokenImpl() {
    }

    @Override
    public TokenModel createTokenFromUser(UserEntity user, Integer validity) {
        Date expirationDate = new Date(new Timestamp(System.currentTimeMillis()).getTime() + TOKEN_VALIDITY * 1000);

        String tokenValue = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return new TokenModel(user.getId(), user.getEmail(), expirationDate, tokenValue);
    }

    @Override
    public TokenModel generateTokenForUser(UserEntity user) {

        if (user == null) return null;

        return createTokenFromUser(user, TOKEN_VALIDITY);
    }

    @Override
    public String getUsernameFromToken(String token) throws TokenValidationException {

        if (token == null) throw new TokenValidationException("Token requis.");

        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
