package ug.visituganda.visituganda.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.dto.response.AuthenticationResponse;
import ug.visituganda.visituganda.dto.request.user_request.BusinessRegisterRequest;
import ug.visituganda.visituganda.dto.request.user_request.CustomerRegisterRequest;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.modal.enums.UserType;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.service_impl.JwtServiceImpl;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;

    public AuthenticationResponse registerCustomer(CustomerRegisterRequest request) {

        // 1️⃣ Password confirmation
        if (!request.password().equals(request.passwordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // 2️⃣ Check duplicate username/email (use trimmed values)
        String username = request.username().trim();
        String email = request.email() != null ? request.email().trim() : null;
        String msisdn = request.msisdn() != null ? request.msisdn().trim() : null;
        String fullName = request.fullName().trim();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (email != null && userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // 3️⃣ Build user entity (SANITIZED)
        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(request.password()))
                .fullName(fullName)
                .email(email)
                .msisdn(msisdn)
                .userType(UserType.CUSTOMER)
                .build();

        // 4️⃣ Save user
        user = userRepository.save(user);

        // 5️⃣ Generate JWT
        var jwt = jwtService.generateToken(user);

        // 6️⃣ Build response
        return AuthenticationResponse.builder()
                .token(jwt)
                .userType(UserType.CUSTOMER.name())
                .redirectUrl("/customer/dashboard")
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .msisdn(user.getMsisdn())
                .build();
    }

    public AuthenticationResponse registerBusiness(BusinessRegisterRequest request) {

        if (!request.password().equals(request.passwordConfirm())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        String username = request.username().trim();
        String email = request.email() != null ? request.email().trim() : null;
        String msisdn = request.msisdn() != null ? request.msisdn().trim() : null;
        String fullName = request.fullName().trim();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        if (email != null && userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        var user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(request.password()))
                .fullName(fullName)
                .email(email)
                .msisdn(msisdn)
                .userType(UserType.BUSINESS)
                .build();

        user = userRepository.save(user);

        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .userType(UserType.BUSINESS.name())
                .redirectUrl("/business/dashboard")
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .msisdn(user.getMsisdn())
                .build();
    }
}

