package com.sport.workout.controller;

import com.sport.workout.configuration.security.jwt.JwtResponse;
import com.sport.workout.configuration.security.jwt.JwtUtil;
import com.sport.workout.dto.AuthsDTO;
import com.sport.workout.dto.UserDto;
import com.sport.workout.exseption.response.ExceptionResponse;
import com.sport.workout.model.User;
import com.sport.workout.response.SuccessfulRegistration;
import com.sport.workout.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthRestControllerV1 {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthRestControllerV1(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "User registration")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful registration",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = SuccessfulRegistration.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "The user cannot be registered, the database is missing a default value",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User already registered",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionResponse.class)
                                    )
                            }
                    ),
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@Parameter(
            description = "Data required for registration"
    ) @RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto.getUser());
        return ResponseEntity.ok(new SuccessfulRegistration(user.getEmail(), user.getDeviceId()));
    }

    @Operation(summary = "User login")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully logged in",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = JwtResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Invalid data sent",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionResponse.class)
                                    )
                            }
                    )
            }
    )
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
