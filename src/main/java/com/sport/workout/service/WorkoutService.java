package com.sport.workout.service;

import com.sport.workout.dto.ExerciseApproachDTO;
import org.springframework.http.ResponseEntity;

public interface WorkoutService {

    ResponseEntity<?> start(String principal);

    ResponseEntity<?> end(String principal);

    ResponseEntity<?> approach(String principal, ExerciseApproachDTO exerciseApproachDTO);

    ResponseEntity<?> findExercises();

    ResponseEntity<?> status(String principal);



}
