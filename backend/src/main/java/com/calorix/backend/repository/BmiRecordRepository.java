package com.calorix.backend.repository;

import com.calorix.backend.entity.BmiRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BmiRecordRepository extends JpaRepository<BmiRecord, Long> {

    List<BmiRecord> findByUserIdOrderByRecordDateDesc(Long userId);
    Optional<BmiRecord> findTopByUserIdOrderByRecordDateDesc(Long userId);

}