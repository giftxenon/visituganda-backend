package ug.visituganda.visituganda.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.dto.response.AuthenticationResponse;
import ug.visituganda.visituganda.dto.request.user_request.BusinessRegisterRequest;
import ug.visituganda.visituganda.dto.request.user_request.CustomerRegisterRequest;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.enums.UserType;
import ug.visituganda.visituganda.repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor   // ← Only this one
public class AuthService {  // ← REMOVE @Builder FROM HERE!

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationResponse registerCustomer(CustomerRegisterRequest request) {

        // 1. Password confirmation check
        if (!request.password().equals(request.passwordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }
        // 2. Check duplicate username


       // 2. Check duplicate username
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .email(request.email())
                .msisdn(request.msisdn())
                .userType(UserType.CUSTOMER)
                .build();

        userRepository.save(user);   // ← Now works because repo is not null!

        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .userType(UserType.CUSTOMER.name())
                .redirectUrl("/customer/dashboard")
                .build();
    }

    public AuthenticationResponse registerBusiness(BusinessRegisterRequest request) {

        if (!request.password().equals(request.passwordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .email(request.email())
                .msisdn(request.msisdn())
                .userType(UserType.BUSINESS)
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .userType(UserType.BUSINESS.name())
                .redirectUrl("/business/dashboard")
                .build();
    }
}