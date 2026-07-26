package com.calorix.backend.mapper;

import com.calorix.backend.dto.goal.GoalRequest;
import com.calorix.backend.dto.goal.GoalResponse;
import com.calorix.backend.entity.Goal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GoalMapper {

    Goal toEntity(GoalRequest dto);

    GoalResponse toResponse(Goal entity);

}