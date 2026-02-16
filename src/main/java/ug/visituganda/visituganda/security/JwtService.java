package ug.visituganda.visituganda.security;

import org.springframework.security.core.userdetails.UserDetails;
import ug.visituganda.visituganda.entity.User;

public interface JwtService {

    // ✅ Generate token from Spring Security UserDetails
    String generateToken(UserDetails userDetails);

    // ✅ Overload: Generate token directly from your User entity
    String generateToken(User user);

    // Extract username from token
    String extractUsername(String token);

    // Validate token against user details
    boolean isTokenValid(String token, UserDetails userDetails);
}
