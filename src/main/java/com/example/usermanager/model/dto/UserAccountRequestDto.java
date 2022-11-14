package com.example.usermanager.model.dto;

import com.example.usermanager.lib.ValidPassword;
import com.example.usermanager.lib.ValidUserName;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAccountRequestDto {
    @ValidUserName
    private String username;
    @ValidPassword
    private String password;
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only latin letters, 1 - 16 symbols")
    private String firstName;
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only latin letters, 1 - 16 symbols")
    private String lastName;
    private String role;
    private String status;
}
