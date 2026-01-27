package ug.visituganda.visituganda.dto.request.user_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BusinessRegisterRequest(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Confirm password is required")
        String passwordConfirm,   // ← NEW FIELD

        @NotBlank(message = "Full name is required")
        String fullName,

        String email,

        String msisdn

) {
    // Custom validation method – runs automatically when @Valid is used
    public void validatePasswordMatch() {
        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}
