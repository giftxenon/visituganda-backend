package ug.visituganda.visituganda.dto.response;

public record LoginResponse(
        String Id,
        boolean success,
        String message,
        Long userId,
        String username,
        String email,
        String msisdn,
        String role
) {}
