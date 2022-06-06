package com.sport.workout.configuration.security;

import com.sport.workout.model.User;
import com.sport.workout.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        return CustomUserDetails
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .isAccountNonExpired(hasAccess)
                .isAccountNonLocked(hasAccess)
                .isCredentialsNonExpired(hasAccess)
                .isEnabled(hasAccess)
                .build();
    }

}
