package ug.visituganda.visituganda.dto.response;


public record LoginResponse(


        boolean success,
        String message,
        Long userId,
        String username,
        String email,
        String msisdn


) {}
