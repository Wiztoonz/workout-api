package com.sport.workout.controller;

import com.sport.workout.configuration.security.jwt.JwtResponse;
import com.sport.workout.configuration.security.jwt.JwtUtil;
import com.sport.workout.dto.AuthsDTO;
import com.sport.workout.dto.UserDto;
import com.sport.workout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public void register(@RequestBody UserDto user) {
        userService.registerUser(user.getUser());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthsDTO auths) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        auths.getEmail(),
                        auths.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwtToken = jwtUtil.generateJwtToken((UserDetails) authenticate.getPrincipal());
        return ResponseEntity.ok(new JwtResponse("Bearer " + jwtToken));
    }

}
