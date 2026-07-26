package com.calorix.backend.repository;

import com.calorix.backend.entity.WaterIntake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WaterIntakeRepository extends JpaRepository<WaterIntake, Long> {

    List<WaterIntake> findByUserIdOrderByRecordDateDesc(Long userId);

    Optional<WaterIntake> findTopByUserIdOrderByRecordDateDesc(Long userId);

    Optional<WaterIntake> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);

    List<WaterIntake> findByUserIdAndRecordDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );
}