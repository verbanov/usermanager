package com.example.usermanager.mapper;

import com.example.usermanager.model.UserAccount;
import com.example.usermanager.model.dto.UserAccountRequestDto;
import com.example.usermanager.model.dto.UserAccountResponseDto;

public interface UserAccountMapper {
    public UserAccountResponseDto modelToDto(UserAccount userAccount);

    public UserAccount dtoToModel(UserAccountRequestDto userAccountRequestDto);
}
