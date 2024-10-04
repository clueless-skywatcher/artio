package io.duskmare.artio.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.duskmare.artio.models.ArtioUser;

@Service
public class JWTTokenProviderService {
    @Value("${security.jwt.token.secret-key}")
    private String jwtSecret;

    public String generateAccessToken(ArtioUser user) {
        try {
            Algorithm algo = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("username", user.getUsername())
                .withExpiresAt(generateExpiryDate())
                .sign(algo);
        } catch (JWTCreationException e) {
            throw e;
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algo = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algo)
                .build()
                .verify(token)
                .getSubject();
        } catch (JWTVerificationException e) {
            throw e;
        }
    }

    private Instant generateExpiryDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("+0530"));
    }
}
