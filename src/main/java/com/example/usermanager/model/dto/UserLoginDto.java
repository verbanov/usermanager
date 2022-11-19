package com.example.usermanager.model.dto;

import com.example.usermanager.lib.ValidPassword;
import com.example.usermanager.lib.ValidUserName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    @ValidUserName
    private String login;
    @ValidPassword
    private String password;
}
