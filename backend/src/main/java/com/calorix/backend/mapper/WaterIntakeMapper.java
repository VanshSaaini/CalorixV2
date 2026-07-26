package com.calorix.backend.mapper;

import com.calorix.backend.dto.water.WaterIntakeRequest;
import com.calorix.backend.dto.water.WaterIntakeResponse;
import com.calorix.backend.entity.WaterIntake;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WaterIntakeMapper {

    WaterIntake toEntity(WaterIntakeRequest dto);

    WaterIntakeResponse toResponse(WaterIntake entity);

}