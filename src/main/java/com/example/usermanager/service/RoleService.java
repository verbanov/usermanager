package com.example.usermanager.service;

import com.example.usermanager.model.Role;

public interface RoleService {
    Role getRoleByRoleName(String roleName);

    Role save(Role role);
}
