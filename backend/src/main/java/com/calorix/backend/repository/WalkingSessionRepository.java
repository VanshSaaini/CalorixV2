package com.calorix.backend.repository;

import com.calorix.backend.entity.WalkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkingSessionRepository extends JpaRepository<WalkingSession, Long> {

    List<WalkingSession> findByUserIdOrderByStartTimeDesc(Long userId);

    WalkingSession findFirstByUserIdOrderByStartTimeDesc(Long userId);
}
