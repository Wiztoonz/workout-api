package com.sport.workout.service.impl;

import com.sport.workout.dto.*;
import com.sport.workout.exseption.UserNotFoundException;
import com.sport.workout.model.*;
import com.sport.workout.repository.UserRepository;
import com.sport.workout.service.ApproachService;
import com.sport.workout.service.ExerciseService;
import com.sport.workout.service.TrainingService;
import com.sport.workout.service.WorkoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final UserRepository userRepository;
    private final TrainingService trainingService;
    private final ExerciseService exerciseService;
    private final ApproachService approachService;

    public WorkoutServiceImpl(UserRepository userRepository,
                              TrainingService trainingService,
                              ExerciseService exerciseService,
                              ApproachService approachService) {
        this.userRepository = userRepository;
        this.trainingService = trainingService;
        this.exerciseService = exerciseService;
        this.approachService = approachService;
    }

    @Override
    public ResponseEntity<?> start(String email) {
        User user = userRepository.findUser(email).orElseThrow(() -> new UserNotFoundException(String.format("User with email: %s is not found!", email)));
        Training training = trainingService.findUserTraining(email, Status.START);
        Map<Object, Object> response = new HashMap<>();
        if (training != null && training.getStatus().equals(Status.START)) {
            response.put("message", "You already started training, finish to start");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            training = trainingService.createTraining(Training
                    .builder()
                    .startTraining(LocalDateTime.now())
                    .user(user)
                    .status(Status.START)
                    .build());
        }
        response.put("message", String.format("Training id: %d, status: %s", training.getId(), training.getStatus()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> end(String principal) {
        Training training = trainingService.findLastUserTraining(principal, Status.START);
        Map<Object, Object> response = new HashMap<>();
        if (training == null) {
            response.put("message", "Nothing to end!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        training.setEndTraining(LocalDateTime.now());
        training.setStatus(Status.END);
        List<Exercise> exercises = training.getExercises();
        List<ExerciseDTO> exerciseDTOS = exercises
                .stream()
                .distinct()
                .map(exercise -> ExerciseDTO
                        .builder()
                        .muscleGroup(exercise.getMuscleGroup())
                        .exercise(exercise.getExercise())
                        .approaches(exercise.getApproaches()
                                .stream()
                                .map(approach -> ApproachDTO
                                        .builder()
                                        .weight(approach.getWeight())
                                        .repetitions(approach.getRepetitions())
                                        .build())
                                .toList())
                        .build()).toList();
        TrainingDTO trainingDTO = TrainingDTO
                .builder()
                .status(training.getStatus())
                .startTraining(training.getStartTraining())
                .endTraining(training.getEndTraining())
                .exercises(exerciseDTOS)
                .build();
        return new ResponseEntity<>(trainingDTO, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> approach(String principal, ExerciseApproachDTO approach) {
        Training training = trainingService.findLastUserTraining(principal, Status.START);
        Exercise exercise = exerciseService.findExercise(approach.getExerciseId());
        Approach newApproach = Approach
                .builder()
                .repetitions(approach.getRepetitions())
                .weight(approach.getWeight())
                .build();
        Approach persistentApproach = approachService.save(newApproach);
        exercise.addApproach(persistentApproach);
        exercise.addTraining(training);

        List<ApproachDTO> approaches = exercise.getApproaches().stream()
                .map(app -> ApproachDTO
                        .builder()
                        .repetitions(app.getRepetitions())
                        .weight(app.getWeight())
                        .build())
                .toList();

        return new ResponseEntity<>(ExerciseDTO
                .builder()
                .muscleGroup(exercise.getMuscleGroup())
                .exercise(exercise.getExercise())
                .approaches(approaches)
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findExercises() {
        List<Exercise> exercises = exerciseService.findAll();
        List<MuscleExerciseDTO> exercisesMap = exercises.stream().map(exercise -> MuscleExerciseDTO
                .builder()
                .id(exercise.getId())
                .exercise(exercise.getExercise())
                .muscle(exercise.getMuscleGroup())
                .build()).sorted(Comparator.comparing(MuscleExerciseDTO::getMuscle)).collect(Collectors.toList());
        return new ResponseEntity<>(exercisesMap, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> status(String principal) {
        Training training;
        Status status;
        training = trainingService.findLastUserTraining(principal, Status.END);
        if (training == null) {
            training = trainingService.findLastUserTraining(principal, Status.START);
        }
        status = switch (training.getStatus()) {
            case START -> Status.START;
            case END -> Status.END;
            default -> Status.UNKNOWN;
        };
        Map<Object, Object> statusResponseMap = new HashMap<>();
        statusResponseMap.put("status", status);
        return new ResponseEntity<>(statusResponseMap, HttpStatus.OK);
    }

}
