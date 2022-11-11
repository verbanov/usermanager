package com.example.usermanager.service;

import com.example.usermanager.exception.DataProcessingException;
import com.example.usermanager.model.Role;
import com.example.usermanager.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return roleRepository.getRoleByRoleName(roleName).orElseThrow(
                () -> new DataProcessingException(
                        "Can't get role with roleName " + roleName));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
