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
import ug.visituganda.visituganda.service.AuthService;
import ug.visituganda.visituganda.service.LoginService;


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
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + ex.getMessage());
        }
    }

    @PostMapping("/register/customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegisterRequest request) {
        try {
            return ResponseEntity.ok(authService.registerCustomer(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Registration failed: " + ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(loginService.login(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed: " + ex.getMessage());
        }
    }
}
