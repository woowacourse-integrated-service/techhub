package com.integrated.techhub.mission.domain.repository;

import com.integrated.techhub.mission.domain.Step;
import com.integrated.techhub.mission.exception.StepNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StepRepository extends JpaRepository<Step, Long> {

    Optional<Step> findByNumber(final Long stepNumber);

    default Step getByNumber(final Long stepNumber) {
        return findByNumber(stepNumber)
                .orElseThrow(() -> new StepNotFoundException(stepNumber));
    }
}
