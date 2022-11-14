package com.example.usermanager.service;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;

public interface RoleService {
    Role save(Role role);

    Role getRoleByRoleName(RoleName roleName);
}
