package ug.visituganda.visituganda.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ug.visituganda.visituganda.dto.LoginRequest;
import ug.visituganda.visituganda.dto.response.AuthenticationResponse;
import ug.visituganda.visituganda.dto.request.user_request.BusinessRegisterRequest;
import ug.visituganda.visituganda.dto.request.user_request.CustomerRegisterRequest;
import ug.visituganda.visituganda.dto.response.LoginResponse;
import ug.visituganda.visituganda.service.AuthService;
import ug.visituganda.visituganda.service.LoginService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final AuthService authService;

    @PostMapping("/register/business")
    public ResponseEntity<AuthenticationResponse> registerBusiness(
            @Valid @RequestBody BusinessRegisterRequest request) {
        try {
            AuthenticationResponse response = authService.registerBusiness(request);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Always return JSON even on errors
            AuthenticationResponse errorResponse = new AuthenticationResponse(
                    null, null, null, null, null, null, null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        try {
            LoginResponse response = loginService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new LoginResponse(
                    false,
                    ex.getMessage(),
                    null, null, null, null
            ));
        } catch (Exception ex) {
            // Fallback for unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(
                            false,
                            "An unexpected error occurred. Please try again.",
                            null, null, null, null
                    ));
        }
    }

    @PostMapping("/register/customer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @Valid @RequestBody CustomerRegisterRequest request) {

        try {
            AuthenticationResponse response = authService.registerCustomer(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            // Known validation error
            AuthenticationResponse errorResponse = new AuthenticationResponse(
                    null, null, null, null, null, null, null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception ex) {
            // Catch-all for unexpected errors
            AuthenticationResponse errorResponse = new AuthenticationResponse(
                    null, null, null, null, null, null, null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
