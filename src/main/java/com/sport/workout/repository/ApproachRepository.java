package com.sport.workout.repository;

import com.sport.workout.model.Approach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproachRepository extends JpaRepository<Approach, Long> {
}
