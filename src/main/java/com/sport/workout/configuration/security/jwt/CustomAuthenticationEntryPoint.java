package com.sport.workout.configuration.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sport.workout.exseption.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ExceptionResponse invalidAuthenticationResponse = invalidAuthenticationResponse(authException, request);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), invalidAuthenticationResponse);
    }

    public ExceptionResponse invalidAuthenticationResponse(AuthenticationException authException, HttpServletRequest request) {
        return ExceptionResponse.builder()
                .message(authException.getMessage())
                .status(HttpStatus.UNAUTHORIZED.name())
                .path(request.getServletPath())
                .build();
    }

}
