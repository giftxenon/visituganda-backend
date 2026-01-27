package ug.visituganda.visituganda.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Login field (email or phone number) is required")
        String loginField,

        @NotBlank(message = "Password is required")
        String password
) {}
