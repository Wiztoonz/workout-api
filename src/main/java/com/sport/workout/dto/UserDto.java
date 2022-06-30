package com.sport.workout.dto;

import com.sport.workout.model.User;

public record UserDto(String email,
                      String password,
                      String deviceId) {


    public User getUser() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .deviceId(this.deviceId)
                .build();
    }

}
