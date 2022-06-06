package com.sport.workout.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class AuthzDTO {

    private String email;
    private String password;

}
