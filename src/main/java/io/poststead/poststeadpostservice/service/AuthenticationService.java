package io.poststead.poststeadpostservice.service;

import io.poststead.poststeadpostservice.exception.user_exception.UserNotFoundException;
import io.poststead.poststeadpostservice.model.Role;
import io.poststead.poststeadpostservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
            .map(user -> User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .credentialsExpired(false)
                .disabled(false)
                .authorities(getAuthorities(user.getRole()))
                .build()
            )
            .orElseThrow(() -> new UserNotFoundException(username));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
}
