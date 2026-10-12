package com.jwt.example.JWTExample3.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTHelper {

    // JWT token validity = 5 hours
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    // Secret key
    // For HS512, use a sufficiently long secret.
    private final String secret =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                    ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30";

    // Convert String secret into SecretKey
    private final SecretKey secretKey;

    public JWTHelper() {
        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }


    // ---------------------------------------------------------
    // Retrieve username from JWT token
    // ---------------------------------------------------------

    public String getUsernameFromToken(String token) {

        return getClaimFromToken(token,Claims::getSubject);
    }


    // ---------------------------------------------------------
    // Retrieve expiration date from JWT token
    // ---------------------------------------------------------

    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token,Claims::getExpiration);
    }


    // ---------------------------------------------------------
    // Retrieve any claim from JWT token
    // ---------------------------------------------------------

    public <T> T getClaimFromToken(String token,Function<Claims, T> claimsResolver)
    {
        final Claims claims = getAllClaimsFromToken(token);
       return claimsResolver.apply(claims);
    }


    // ---------------------------------------------------------
    // Retrieve all claims from JWT token
    // ---------------------------------------------------------

    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    // ---------------------------------------------------------
    // Check whether token has expired
    // ---------------------------------------------------------

    private Boolean isTokenExpired(String token) {

        final Date expiration =
                getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }


    // ---------------------------------------------------------
    // Generate token for user
    // ---------------------------------------------------------

    public String generateToken(UserDetails userDetails)
    {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }
        // Generate JWT token
    private String doGenerateToken(Map<String, Object> claims,String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + JWT_TOKEN_VALIDITY * 1000
                        )
                )
                .signWith(secretKey)
                .compact();
    }


    // ---------------------------------------------------------
    // Validate JWT token
    // ---------------------------------------------------------

    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username =  getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }
}