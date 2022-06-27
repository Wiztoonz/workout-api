package com.sport.workout.repository;

import com.sport.workout.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void checkIfUserIsFind() {
        // given
        String email = "test@mail.com";
        User user = new User(1L, "test@mail.com", anyString(), anyString(), anyString(), anyString(), anyList());
        userRepository.save(user);

        // when
        User foundUser = userRepository.findUser(email).get();

        // then
        assertThat(foundUser).isEqualTo(user);
    }

}