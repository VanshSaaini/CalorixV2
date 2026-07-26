package com.calorix.backend.repository;

import com.calorix.backend.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {

    List<Goal> findByUserId(Long userId);

    Optional<Goal> findFirstByUserIdAndCompletedFalse(Long userId);

    List<Goal> findByUserIdOrderByTargetDateAsc(Long userId);

    List<Goal> findByUserIdAndCompleted(Long userId, Boolean completed);

}