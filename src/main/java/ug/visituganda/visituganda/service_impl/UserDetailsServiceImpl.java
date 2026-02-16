package ug.visituganda.visituganda.service_impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ug.visituganda.visituganda.entity.User;
import ug.visituganda.visituganda.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Wrap your User entity into Spring Security UserDetails
        org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User.builder();
        builder.username(user.getUsername());
        builder.password(user.getPassword());
        builder.roles(user.getUserType().name()); // simple role
        return builder.build();
    }
}

