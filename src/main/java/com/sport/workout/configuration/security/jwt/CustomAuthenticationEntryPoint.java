package com.sport.workout.configuration.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Map<String, Object> invalidAuthenticationResponse = invalidAuthenticationResponse(authException, request);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), invalidAuthenticationResponse);
    }

    public Map<String, Object> invalidAuthenticationResponse(AuthenticationException authException, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", authException.getMessage());
        response.put("status", HttpStatus.UNAUTHORIZED);
        response.put("path", request.getServletPath());
        return response;
    }

}
