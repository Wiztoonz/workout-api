package com.sport.workout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseApproachDTO {

    private Long exerciseId;
    private Integer repetitions;
    private Integer weight;

}
