package com.calorix.backend.mapper;

import com.calorix.backend.dto.weight.WeightRecordRequest;
import com.calorix.backend.dto.weight.WeightRecordResponse;
import com.calorix.backend.entity.WeightRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeightRecordMapper {

    WeightRecord toEntity(WeightRecordRequest dto);

    WeightRecordResponse toResponse(WeightRecord entity);

}