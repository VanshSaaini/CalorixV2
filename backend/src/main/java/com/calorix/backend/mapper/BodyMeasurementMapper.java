package com.calorix.backend.mapper;

import com.calorix.backend.dto.bodymeasurement.BodyMeasurementRequest;
import com.calorix.backend.dto.bodymeasurement.BodyMeasurementResponse;
import com.calorix.backend.entity.BodyMeasurement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BodyMeasurementMapper {

    BodyMeasurement toEntity(BodyMeasurementRequest dto);

    BodyMeasurementResponse toResponse(BodyMeasurement entity);

}