package com.calorix.backend.mapper;

import com.calorix.backend.dto.role.RoleResponse;
import com.calorix.backend.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse toResponse(Role role);

}