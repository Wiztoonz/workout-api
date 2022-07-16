package com.sport.workout.exseption.controller;

import com.sport.workout.exseption.RoleNotFoundException;
import com.sport.workout.exseption.UserAlreadyExistsException;
import com.sport.workout.exseption.UserNotFoundException;
import com.sport.workout.exseption.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class DatabaseExceptionController {

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<?> roleNotCreatedInDatabase(HttpServletRequest req, RoleNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(status.name())
                .path(req.getRequestURL().toString())
                .build();
        return ResponseEntity.status(status).body(exceptionResponse);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<?> userAlreadyExists(HttpServletRequest req, UserAlreadyExistsException e) {
        HttpStatus status = HttpStatus.CONFLICT;
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(status.name())
                .path(req.getRequestURL().toString())
                .build();
        return ResponseEntity.status(status).body(exceptionResponse);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<?> userNotFoundException(HttpServletRequest req, UserNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(e.getMessage())
                .status(status.name())
                .path(req.getRequestURL().toString())
                .build();
        return ResponseEntity.status(status).body(exceptionResponse);
    }

}
