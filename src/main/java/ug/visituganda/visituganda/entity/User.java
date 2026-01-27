package ug.visituganda.visituganda.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ug.visituganda.visituganda.modal.enums.UserType;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String msisdn;

    @Column
    private String email;

    @Column
    private String fullName;

    // ←←← CORRECT: instance field + @Enumerated
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;        // ← NOT static! Remove "static" here

    // Spring Security authorities based on userType
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Safe null check (in case of bad data)
        if (userType == null) {
            return List.of();
        }
        return List.of(new SimpleGrantedAuthority("ROLE_" + userType.name()));
    }

    @Override
    public String getUsername() { return username; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}