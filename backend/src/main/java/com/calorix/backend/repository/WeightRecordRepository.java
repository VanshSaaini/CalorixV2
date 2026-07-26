package com.calorix.backend.repository;

import com.calorix.backend.entity.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {

    List<WeightRecord> findByUserIdOrderByRecordDateDesc(Long userId);

    Optional<WeightRecord> findTopByUserIdOrderByRecordDateDesc(Long userId);

    Optional<WeightRecord> findTopByUserIdOrderByWeightDesc(Long userId);

    Optional<WeightRecord> findTopByUserIdOrderByWeightAsc(Long userId);

}