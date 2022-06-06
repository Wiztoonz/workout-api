package com.sport.workout.service;

import com.sport.workout.model.Status;
import com.sport.workout.model.Training;

public interface TrainingService {

    Training createTraining(Training training);

    Training findUserTraining(String email, Status status);

    Training findLastUserTraining(String email, Status status);

}
