package com.sport.workout.controller;

import com.sport.workout.dto.AuthzDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authz")
public class AuthzRestControllerV1 {

    @PostMapping("/login")
    public void login(@RequestBody AuthzDTO authz) {
        String email = authz.getEmail();
        String password = authz.getPassword();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @PostMapping("/logout")
    public void logout() {

    }

}
