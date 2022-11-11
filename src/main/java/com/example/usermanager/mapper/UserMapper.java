package com.example.usermanager.mapper;

import com.example.usermanager.model.User;
import com.example.usermanager.model.dto.UserRequestDto;
import com.example.usermanager.model.dto.UserResponseDto;

public interface UserMapper {
    public UserResponseDto modelToDto(User user);

    public User dtoToModel(UserRequestDto userRequestDto);
}
