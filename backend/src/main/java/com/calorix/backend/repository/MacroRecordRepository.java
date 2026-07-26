package com.calorix.backend.repository;

import com.calorix.backend.entity.MacroRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.time.LocalDate;

import java.util.List;

public interface MacroRecordRepository extends JpaRepository<MacroRecord, Long> {

    List<MacroRecord> findByUserIdOrderByRecordDateDesc(Long userId);

    Optional<MacroRecord> findTopByUserIdOrderByRecordDateDesc(Long userId);

    List<MacroRecord> findByUserIdAndRecordDateBetween(
            Long userId,
            LocalDate start,
            LocalDate end);

}