package com.example.project.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
/** Creates and validates signed JWTs used for API authentication. */
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMillis;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long expirationMillis) {
        // The signing key must be long enough for the HMAC-SHA256 algorithm.
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("app.jwt.secret must contain at least 32 characters");
        }

        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMillis = expirationMillis;
    }

    /** Creates a token whose subject identifies the authenticated user. */
    public String generateToken(String subject) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Reads the user identifier stored in the token subject claim. */
    public String extractSubject(String token) {
        return parseClaims(token).getSubject();
    }

    /** Confirms that the token belongs to the expected user and has not expired. */
    public boolean isTokenValid(String token, String expectedSubject) {
        try {
            Claims claims = parseClaims(token);
            return expectedSubject.equals(claims.getSubject())
                    && claims.getExpiration().after(new Date());
        } catch (RuntimeException exception) {
            return false;
        }
    }

    /** Verifies the signature and parses the claims in a JWT. */
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}