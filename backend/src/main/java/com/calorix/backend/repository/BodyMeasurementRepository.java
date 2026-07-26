package com.calorix.backend.repository;

import com.calorix.backend.entity.BodyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BodyMeasurementRepository extends JpaRepository<BodyMeasurement, Long> {

    List<BodyMeasurement> findByUserIdOrderByRecordDateDesc(Long userId);

    Optional<BodyMeasurement> findTopByUserIdOrderByRecordDateDesc(Long userId);

}