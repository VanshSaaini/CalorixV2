package com.calorix.backend.mapper;

import com.calorix.backend.dto.calories.DailyCaloriesRequest;
import com.calorix.backend.dto.calories.DailyCaloriesResponse;
import com.calorix.backend.entity.DailyCalories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DailyCaloriesMapper {

    DailyCalories toEntity(DailyCaloriesRequest dto);

    DailyCaloriesResponse toResponse(DailyCalories entity);

}