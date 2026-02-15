package ug.visituganda.visituganda.service_impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.dto.LoginRequest;
import ug.visituganda.visituganda.dto.response.LoginResponse;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.repository.UserRepository;
import ug.visituganda.visituganda.service.LoginService;


@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        String login = request.loginField().trim();

        User user = userRepository.findByUsername(login)
                .or(() -> userRepository.findByEmail(login))
                .or(() -> userRepository.findByMsisdn(login))
                .orElseThrow(() ->
                        new IllegalArgumentException("Username / Email / Phone not found")
                );

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        return new LoginResponse(
                null,
                true,
                "Login successful",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getMsisdn(),
                user.getUserType().name()   // ✅ THIS IS THE FIX
        );
    }
}
