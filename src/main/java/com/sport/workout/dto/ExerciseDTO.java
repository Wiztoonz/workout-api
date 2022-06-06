package com.sport.workout.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDTO {

    private String exercise;
    private String muscleGroup;
    private List<ApproachDTO> approaches;

}
