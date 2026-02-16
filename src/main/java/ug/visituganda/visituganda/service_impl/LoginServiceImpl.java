package ug.visituganda.visituganda.service_impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.dto.LoginRequest;
import ug.visituganda.visituganda.dto.response.AuthenticationResponse;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.service.LoginService;
import ug.visituganda.visituganda.service_impl.JwtServiceImpl;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtService;

    @Override
    public AuthenticationResponse login(LoginRequest request) {

        String login = request.loginField().trim();

        // 1️⃣ Fetch user by username/email/phone
        User user = userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login))
                .or(() -> userRepository.findByMsisdn(login))
                .orElseThrow(() -> new IllegalArgumentException("Username / Email / Phone not found"));

        // 2️⃣ Authenticate using Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        request.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3️⃣ Cast principal to UserDetails for JWT generation
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userDetails);

        // 4️⃣ Return full AuthenticationResponse
        return AuthenticationResponse.builder()
                .token(jwt)
                .userType(user.getUserType().name())
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .msisdn(user.getMsisdn())
                .redirectUrl(user.getUserType() == null
                        ? "/"
                        : user.getUserType() == ug.visituganda.visituganda.modal.enums.UserType.CUSTOMER
                        ? "/customer/dashboard"
                        : "/business/dashboard")
                .build();
    }
}
