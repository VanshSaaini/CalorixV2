package com.calorix.backend.mapper;

import com.calorix.backend.dto.bmr.BmrRecordRequest;
import com.calorix.backend.dto.bmr.BmrRecordResponse;
import com.calorix.backend.entity.BmrRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BmrRecordMapper {

    BmrRecord toEntity(BmrRecordRequest dto);

    BmrRecordResponse toResponse(BmrRecord entity);

}