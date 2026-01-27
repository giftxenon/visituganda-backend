/*
package ug.visituganda.visituganda.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.processing.Pattern;
import ug.visituganda.visituganda.validation.PasswordMatch;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatch(password = "password", confirmPassword = "confirmPassword")
public class RegisterRequest {


    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "Enter a valid international phone number (e.g. +25677xxxxxxx or 077xxxxxxx)"
    )
    private String msisdn;  // Will be stored with +country code

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;

    @NotBlank(message = "Full name is required")
    private String fullName;


}
*/
