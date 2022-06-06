package com.sport.workout.repository;

import com.sport.workout.model.Status;
import com.sport.workout.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    Training findUserTraining(String email, Status status);

    Training findLastUserTraining(String email, Status status);

}
