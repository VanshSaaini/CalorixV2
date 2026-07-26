package com.calorix.backend.mapper;

import com.calorix.backend.dto.progressphoto.ProgressPhotoRequest;
import com.calorix.backend.dto.progressphoto.ProgressPhotoResponse;
import com.calorix.backend.entity.ProgressPhoto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProgressPhotoMapper {

    ProgressPhoto toEntity(ProgressPhotoRequest dto);

    ProgressPhotoResponse toResponse(ProgressPhoto entity);

}