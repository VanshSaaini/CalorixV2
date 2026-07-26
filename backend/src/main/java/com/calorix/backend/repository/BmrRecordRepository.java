package com.calorix.backend.repository;

import com.calorix.backend.entity.BmrRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BmrRecordRepository extends JpaRepository<BmrRecord, Long> {

    Optional<BmrRecord> findTopByUserIdOrderByRecordDateDesc(Long userId);

    List<BmrRecord> findByUserIdOrderByRecordDateDesc(Long userId);

}