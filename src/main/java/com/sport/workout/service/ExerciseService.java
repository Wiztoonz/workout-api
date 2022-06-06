package com.sport.workout.service;

import com.sport.workout.model.Exercise;

import java.util.List;

public interface ExerciseService {

    List<Exercise> findAll();

    Exercise findExercise(Long id);


}
