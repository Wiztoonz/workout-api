package com.sport.workout.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class AuthsDTO {

    private String email;
    private String password;

}
