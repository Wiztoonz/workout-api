package com.sport.workout.dto;

import com.sport.workout.model.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainingDTO {

    private LocalDateTime startTraining;
    private LocalDateTime endTraining;
    private Status status;
    private List<ExerciseDTO> exercises;


}
