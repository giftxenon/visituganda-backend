/*
package ug.visituganda.visituganda.service;

//import org.springframework.security.core.userdetails.UserModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.entity.UserModel;          // ← Your JPA entity
import ug.visituganda.visituganda.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor injection (recommended)
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registration method - saves your own UserModel entity
    public UserModel register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        UserModel user = (UserModel) UserModel.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();

        return userRepository.save(user);  // Saves your JPA @Entity UserModel
    }

    // Required by Spring Security - must return UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("UserModel not found: " + username));

        return org.springframework.security.core.userdetails.UserModel.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")                    // Change/add roles as needed
                .build();
    }
}*/
