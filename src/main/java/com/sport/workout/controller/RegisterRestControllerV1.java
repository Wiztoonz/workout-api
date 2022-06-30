package com.sport.workout.controller;

import com.sport.workout.dto.UserDto;
import com.sport.workout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterRestControllerV1 {

    private final UserService userService;

    @Autowired
    public RegisterRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void register(@RequestBody UserDto user) {
        userService.registerUser(user.getUser());
    }

}
