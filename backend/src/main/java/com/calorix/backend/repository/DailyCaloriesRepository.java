package com.calorix.backend.repository;

import com.calorix.backend.entity.DailyCalories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyCaloriesRepository extends JpaRepository<DailyCalories, Long> {

    List<DailyCalories> findByUserIdOrderByRecordDateDesc(Long userId);

    Optional<DailyCalories> findTopByUserIdOrderByRecordDateDesc(Long userId);

    Optional<DailyCalories> findByUserIdAndRecordDate(Long userId, LocalDate recordDate);

    List<DailyCalories> findByUserIdAndRecordDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );
}