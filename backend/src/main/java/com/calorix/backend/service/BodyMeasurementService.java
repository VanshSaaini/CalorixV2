package com.calorix.backend.service;

import com.calorix.backend.dto.bodymeasurement.BodyMeasurementRequest;
import com.calorix.backend.dto.bodymeasurement.BodyMeasurementResponse;

import java.util.List;

public interface BodyMeasurementService {

    BodyMeasurementResponse saveMeasurement(Long userId, BodyMeasurementRequest request);

    BodyMeasurementResponse updateMeasurement(Long id, BodyMeasurementRequest request);

    BodyMeasurementResponse getMeasurement(Long id);

    List<BodyMeasurementResponse> getUserMeasurements(Long userId);

    BodyMeasurementResponse getLatestMeasurement(Long userId);

    void deleteMeasurement(Long id);
}