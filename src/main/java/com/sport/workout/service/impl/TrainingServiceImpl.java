package com.sport.workout.service.impl;

import com.sport.workout.model.Status;
import com.sport.workout.model.Training;
import com.sport.workout.repository.TrainingRepository;
import com.sport.workout.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    @Transactional
    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public Training findUserTraining(String email, Status status) {
        return trainingRepository.findUserTraining(email, status);
    }

    @Override
    public Training findLastUserTraining(String email, Status status) {
        return trainingRepository.findLastUserTraining(email, status);
    }

}
