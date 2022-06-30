package com.sport.workout.controller;

import com.sport.workout.dto.AuthzDTO;
import com.sport.workout.model.User;
import com.sport.workout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthsRestControllerV1 {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthsRestControllerV1(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public void login(@RequestBody AuthzDTO authz) {
        String email = authz.getEmail();
        User user = userService.findUser(email).orElseThrow(() -> new UsernameNotFoundException("User with " + email + " not found."));
        if (email.equalsIgnoreCase(user.getEmail()) && passwordEncoder.matches(authz.getPassword(), user.getPassword())) {
            List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream().map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getRoleName().name())).toList();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw  new UsernameNotFoundException("User with " + email + " not found.");
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }


}