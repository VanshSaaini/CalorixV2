package com.calorix.backend.mapper;

import com.calorix.backend.dto.bmi.BmiRecordRequest;
import com.calorix.backend.dto.bmi.BmiRecordResponse;
import com.calorix.backend.entity.BmiRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BmiRecordMapper {

    BmiRecord toEntity(BmiRecordRequest dto);

    BmiRecordResponse toResponse(BmiRecord entity);

}