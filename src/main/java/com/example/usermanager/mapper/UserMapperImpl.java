package com.example.usermanager.mapper;

import com.example.usermanager.model.Status;
import com.example.usermanager.model.User;
import com.example.usermanager.model.dto.UserRequestDto;
import com.example.usermanager.model.dto.UserResponseDto;
import com.example.usermanager.service.RoleService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper {
    private final RoleService roleService;

    public UserMapperImpl(RoleService roleService) {
        this.roleService = roleService;
    }
//    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//    public UserMapperImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }

    @Override
    public UserResponseDto modelToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setRoles(user.getRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toSet()));
        userResponseDto.setStatus(user.getStatus().name());
        userResponseDto.setCreatedAt(user.getCreatedAt().toString());
        return userResponseDto;
    }

    @Override
    public User dtoToModel(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        //user.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setRoles(userRequestDto.getRoles()
                .stream()
                .map(roleService::getRoleByRoleName)
                .collect(Collectors.toSet()));
        user.setStatus(Status.valueOf(userRequestDto.getStatus()));
        return user;
    }
}
