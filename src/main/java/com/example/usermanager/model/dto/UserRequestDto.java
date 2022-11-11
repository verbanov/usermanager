package com.example.usermanager.model.dto;

import java.util.Set;
import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private String status;
}
