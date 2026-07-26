package com.calorix.backend.service.impl;

import com.calorix.backend.dto.role.RoleRequest;
import com.calorix.backend.dto.role.RoleResponse;
import com.calorix.backend.entity.Role;
import com.calorix.backend.exception.ResourceNotFoundException;
import com.calorix.backend.mapper.RoleMapper;
import com.calorix.backend.repository.RoleRepository;
import com.calorix.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleRequest request) {

        if (roleRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Role already exists.");
        }

        Role role = Role.builder()
                .name(request.getName())
                .build();

        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse updateRole(Long id, RoleRequest request) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + id));

        role.setName(request.getName());

        return roleMapper.toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + id));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found with id: " + id));

        roleRepository.delete(role);
    }
}