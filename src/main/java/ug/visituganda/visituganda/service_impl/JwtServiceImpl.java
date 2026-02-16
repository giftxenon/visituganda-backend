package ug.visituganda.visituganda.service_impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.security.JwtService;

import java.security.Key;
import java.util.Date;


@Service
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    // 🔹 Helper method to get signing key safely
    private Key getSigningKey() {
        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalStateException("JWT secret must be at least 32 characters long");
        }
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // 🔹 Centralized token creation
    private String buildToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // 🔹 Generate token from UserDetails (login flow)
    @Override
    public String generateToken(UserDetails userDetails) {
        if (userDetails == null || userDetails.getUsername() == null) {
            throw new IllegalArgumentException("UserDetails or username cannot be null");
        }
        return buildToken(userDetails.getUsername());
    }

    // 🔹 Generate token from User entity (registration flow)
    public String generateToken(User user) {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException("User or username cannot be null");
        }
        return buildToken(user.getUsername());
    }

    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (token == null || userDetails == null) return false;
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
