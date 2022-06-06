package com.sport.workout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MuscleExerciseDTO {

    private Long id;
    private String exercise;
    private String muscle;

}
