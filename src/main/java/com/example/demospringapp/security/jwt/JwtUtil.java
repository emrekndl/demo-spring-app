/**
 * Utility class for generating and validating JSON Web Tokens (JWT) used for authentication and authorization.
 *
 * The `generateToken` method creates a new JWT token for the given `User` object, with an expiration time of 5 minutes.
 *
 * The `getClaimsFromToken` method verifies the signature of the given JWT token and returns the claims (payload) of the token.
 *
 * The `isTokenValid` method checks if the given JWT token is valid, which includes checking if the token is not expired.
 *
 * The `getSigningKey` method returns the secret key used to sign and verify the JWT tokens.
 */
package com.example.demospringapp.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

    public static String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .expiration(new Date(System.currentTimeMillis() + 300_000)) // 5 minutes
                .signWith(getSigningKey())
                .compact();
    }

    public static Claims getClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean isTokenValid(String token) {
        // can add more validation here, e.g. if token is expired, not signed etc
        return !isExpired(token);
    }

    private static boolean isExpired(String token) {
        return getClaimsFromToken(token)
            .getExpiration()
            .before(new Date());
    }

    private static SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("yourSecretKeyAndItMustBeLongeEnoughForSecurity");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
