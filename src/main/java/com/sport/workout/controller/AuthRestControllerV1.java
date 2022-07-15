package com.sport.workout.controller;

import com.sport.workout.configuration.security.jwt.JwtResponse;
import com.sport.workout.configuration.security.jwt.JwtUtil;
import com.sport.workout.dto.AuthsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthRestControllerV1(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthsDTO auths) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auths.getEmail(),
                        auths.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        System.out.println(authenticate);
        String jwtToken = jwtUtil.generateJwtToken((UserDetails) authenticate.getPrincipal());

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }


}