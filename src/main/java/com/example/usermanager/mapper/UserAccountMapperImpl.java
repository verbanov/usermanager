package com.example.usermanager.mapper;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.model.dto.UserAccountRequestDto;
import com.example.usermanager.model.dto.UserAccountResponseDto;
import com.example.usermanager.service.RoleService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapperImpl implements UserAccountMapper {
    private final RoleService roleService;

    public UserAccountMapperImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public UserAccountResponseDto modelToDto(UserAccount userAccount) {
        UserAccountResponseDto userAccountResponseDto = new UserAccountResponseDto();
        userAccountResponseDto.setId(userAccount.getId());
        userAccountResponseDto.setUsername(userAccount.getUsername());
        userAccountResponseDto.setFirstName(userAccount.getFirstName());
        userAccountResponseDto.setLastName(userAccount.getLastName());
        userAccountResponseDto.setRoles(userAccount.getRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toSet()));
        userAccountResponseDto.setStatus(userAccount.getStatus().name());
        userAccountResponseDto.setCreatedAt(userAccount.getCreatedAt().toString());
        return userAccountResponseDto;
    }

    @Override
    public UserAccount dtoToModel(UserAccountRequestDto userAccountRequestDto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(userAccountRequestDto.getUsername());
        userAccount.setPassword(userAccountRequestDto.getPassword());
        userAccount.setFirstName(userAccountRequestDto.getFirstName());
        userAccount.setLastName(userAccountRequestDto.getLastName());
        RoleName roleName = RoleName.valueOf(userAccountRequestDto.getRole());
        Role role = roleService.getRoleByRoleName(roleName);
        userAccount.setRoles(Set.of(role));
        userAccount.setStatus(Status.valueOf(userAccountRequestDto.getStatus()));
        return userAccount;
    }
}
