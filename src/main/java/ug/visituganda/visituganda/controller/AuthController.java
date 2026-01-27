package ug.visituganda.visituganda.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            @RequestBody BusinessRegisterRequest request) {
        return ResponseEntity.ok(authService.registerBusiness(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(loginService.login(request));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new LoginResponse(
                    false,
                    ex.getMessage(),
                    null, null, null, null
            ));
        }
    }

    @PostMapping("/register/customer")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @Valid @RequestBody CustomerRegisterRequest request) {

        try {
            // Call service to register customer
            AuthenticationResponse response = authService.registerCustomer(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            // Return an AuthenticationResponse with all fields null except message if needed
            AuthenticationResponse errorResponse = new AuthenticationResponse(
                    null,       // token
                    null,       // userType
                    null,       // redirectUrl
                    null,       // userId
                    null,       // username
                    null,       // email
                    null        // msisdn
            );

            // Optionally, log or handle ex.getMessage() elsewhere if you want to send it separately
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}