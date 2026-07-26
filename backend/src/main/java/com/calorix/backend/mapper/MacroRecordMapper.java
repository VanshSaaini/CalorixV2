package com.calorix.backend.mapper;

import com.calorix.backend.dto.macro.MacroRecordRequest;
import com.calorix.backend.dto.macro.MacroRecordResponse;
import com.calorix.backend.entity.MacroRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MacroRecordMapper {

    MacroRecord toEntity(MacroRecordRequest dto);

    MacroRecordResponse toResponse(MacroRecord entity);

}