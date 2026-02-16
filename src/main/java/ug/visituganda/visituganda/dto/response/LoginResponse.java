package ug.visituganda.visituganda.dto.response;

public record LoginResponse(
        boolean success,
        String message,
        String token,      // 🔥 JWT TOKEN (REQUIRED)
        Long userId,
        String username,
        String email,
        String msisdn,
        String role
) {}
