package com.calorix.backend.service;

import com.calorix.backend.dto.role.RoleResponse;
import com.calorix.backend.dto.role.RoleRequest;

import java.util.List;

public interface RoleService {

    RoleResponse createRole(RoleRequest request);

    RoleResponse updateRole(Long id, RoleRequest request);

    RoleResponse getRoleById(Long id);

    List<RoleResponse> getAllRoles();

    void deleteRole(Long id);

}