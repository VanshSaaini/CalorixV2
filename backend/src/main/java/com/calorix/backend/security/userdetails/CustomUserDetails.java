package com.calorix.backend.security.userdetails;

import com.calorix.backend.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (user.getRole() == null || user.getRole().getName() == null) {
            return Collections.emptyList();
        }

        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().getName())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Spring Security uses email as the username.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Return true if you don't implement email verification.
     * If you do, use:
     * return user.isEmailVerified();
     */
    @Override
    public boolean isEnabled() {
        return true;
        // return user.isEmailVerified();
    }

    /**
     * Convenience methods
     */
    public Long getId() {
        return user.getId();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public User getUserEntity() {
        return user;
    }
}