package com.sport.workout.dto;

import com.sport.workout.model.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record UserDto(String email,
                      String password,
                      String deviceId) {

    @Schema(hidden = true)
    public User getUser() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .deviceId(this.deviceId)
                .build();
    }

}
