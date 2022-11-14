package com.example.usermanager.repository;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("from Role r where r.roleName = :roleName")
    Role getRoleByRoleName(RoleName roleName);
}
