package com.sport.workout.service.impl;

import com.sport.workout.model.Approach;
import com.sport.workout.repository.ApproachRepository;
import com.sport.workout.service.ApproachService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApproachServiceImpl implements ApproachService {

    private final ApproachRepository approachRepository;

    public ApproachServiceImpl(ApproachRepository approachRepository) {
        this.approachRepository = approachRepository;
    }

    @Override
    @Transactional
    public Approach save(Approach approach) {
        return approachRepository.save(approach);
    }
}
