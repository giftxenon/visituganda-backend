package ug.visituganda.visituganda.service_impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.dto.LoginRequest;
import ug.visituganda.visituganda.dto.response.LoginResponse;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.service.LoginService;
// import ug.visituganda.visituganda.service.JwtService;  // Uncomment if adding JWT

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    // private final JwtService jwtService;  // Uncomment if adding JWT

    @Override
    public LoginResponse login(LoginRequest request) {

        String login = request.loginField().trim();

        // FIND USER BY USERNAME, EMAIL, OR PHONE
        User user = userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login))
                .or(() -> userRepository.findByMsisdn(login))
                .orElseThrow(() -> new IllegalArgumentException("Invalid login credentials (username, email, or phone not found)"));

        // MATCH PASSWORD
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");  // Throw to handle as error, or return false as before
        }

        // SUCCESS - Optionally generate JWT here if needed
        // String jwt = jwtService.generateToken(user);

        return new LoginResponse(
                true,
                "Login successful",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getMsisdn()
                // jwt  // Add to DTO if using
        );
    }
}