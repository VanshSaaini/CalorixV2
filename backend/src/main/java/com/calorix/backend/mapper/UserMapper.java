package com.calorix.backend.mapper;

import com.calorix.backend.dto.user.UserRequest;
import com.calorix.backend.dto.user.UserResponse;
import com.calorix.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequest request);

    @Mapping(target = "role", source = "role.name")
    UserResponse toResponse(User user);

}