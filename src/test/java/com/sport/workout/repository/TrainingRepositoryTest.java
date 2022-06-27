package com.sport.workout.repository;

import com.sport.workout.model.Status;
import com.sport.workout.model.Training;
import com.sport.workout.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
class TrainingRepositoryTest {

    @Autowired
    private TrainingRepository trainingRepository;

    @AfterEach
    void tearDown() {
        trainingRepository.deleteAll();
    }

    @Test
    void checkIfUserHasTraining() {
        // given
        User user = User.builder()
                .id(1L)
                .email("test@mail.com")
                .password("testpassword")
                .deviceId(UUID.randomUUID().toString())
                .firstName("firstname")
                .lastName("lastname")
                .build();
        Training training = Training.builder()
                .id(1L)
                .startTraining(LocalDateTime.now())
                .endTraining(LocalDateTime.now().plusHours(1L))
                .status(Status.UNKNOWN)
                .exercises(List.of())
                .build();
        training.addUser(user);
        trainingRepository.save(training);

        // when
        Training foundUserTraining = trainingRepository.findUserTraining(user.getEmail(), training.getStatus());

        // then
        assertThat(foundUserTraining).isEqualTo(training);
    }

    @Test
    @Disabled
    void findLastUserTraining() {
    }

}