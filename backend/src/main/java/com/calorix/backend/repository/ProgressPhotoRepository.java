package com.calorix.backend.repository;

import com.calorix.backend.entity.ProgressPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProgressPhotoRepository extends JpaRepository<ProgressPhoto, Long> {

    List<ProgressPhoto> findByUserIdOrderByRecordDateDesc(Long userId);

    Optional<ProgressPhoto> findTopByUserIdOrderByRecordDateDesc(Long userId);

    Optional<ProgressPhoto> findByUserIdAndRecordDate(
            Long userId,
            LocalDate recordDate
    );

    List<ProgressPhoto> findByUserIdAndRecordDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );
}