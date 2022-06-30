package com.sport.workout.configuration.security;

import com.sport.workout.model.User;
import com.sport.workout.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUser(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User with email: %s is not found.", email))
                );
        boolean hasAccess = true;
        List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName().name()))
                .toList();
        return CustomUserDetails
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .isAccountNonExpired(hasAccess)
                .isAccountNonLocked(hasAccess)
                .isCredentialsNonExpired(hasAccess)
                .isEnabled(hasAccess)
                .build();
    }

}
