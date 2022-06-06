package com.sport.workout.service.impl;

import com.sport.workout.model.Exercise;
import com.sport.workout.repository.ExerciseRepository;
import com.sport.workout.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }


    @Override
    public Exercise findExercise(Long id) {
        return exerciseRepository.findById(id).get();
    }

}
