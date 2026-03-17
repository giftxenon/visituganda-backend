package ug.visituganda.visituganda.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ug.visituganda.visituganda.dto.LoginRequest;
import ug.visituganda.visituganda.dto.response.AuthenticationResponse;
import ug.visituganda.visituganda.dto.request.user_request.BusinessRegisterRequest;
import ug.visituganda.visituganda.dto.request.user_request.CustomerRegisterRequest;
import ug.visituganda.visituganda.dto.response.LoginResponse;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.service.AuthService;
import ug.visituganda.visituganda.service.LoginService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;
    private final AuthService authService;


    @PostMapping("/register/business")
    public ResponseEntity<?> registerBusiness(@Valid @RequestBody BusinessRegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.registerBusiness(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", ex.getMessage())); // make same as customer
        } catch (Exception ex) {
            log.error("Business registration failed", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Registration failed. Please try again later."));
        }
    }

    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegisterRequest request) {
        try {
            // Success response
            return ResponseEntity.ok(authService.registerCustomer(request));
        } catch (IllegalArgumentException ex) {
            // Validation or business errors → 400
            return ResponseEntity.badRequest()
                    .body(Map.of("message", ex.getMessage()));
        } catch (Exception ex) {
            // Unexpected server errors → 500
            ex.printStackTrace(); // Log the full stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Registration failed. Please try again later."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        AuthenticationResponse response = authService.login(
                request.loginField(),
                request.password()
        );

        return ResponseEntity.ok(response);
    }
}
