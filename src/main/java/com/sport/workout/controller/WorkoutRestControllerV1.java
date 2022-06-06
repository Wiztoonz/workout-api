package com.sport.workout.controller;

import com.sport.workout.dto.ExerciseApproachDTO;
import com.sport.workout.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workout")
public class WorkoutRestControllerV1 {
    private final WorkoutService workoutService;

    @Autowired
    public WorkoutRestControllerV1(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/start/training")
    public ResponseEntity<?> startTraining(@AuthenticationPrincipal String principal) {
        return workoutService.start(principal);
    }

    @PostMapping("/end/training")
    public ResponseEntity<?> endTraining(@AuthenticationPrincipal String principal) {
        return workoutService.end(principal);
    }

    @PostMapping("/start/exercise")
    public ResponseEntity<?> doApproach(@AuthenticationPrincipal String principal, @RequestBody ExerciseApproachDTO approach) {
        return workoutService.approach(principal, approach);
    }

    @GetMapping("/exercises")
    public ResponseEntity<?> doExercise() {
        return workoutService.findExercises();
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@AuthenticationPrincipal String principal) {
        return workoutService.status(principal);
    }

}
