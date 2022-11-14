package com.example.usermanager.model.dto;

import java.util.Set;
import lombok.Data;

@Data
public class UserAccountResponseDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private String status;
    private String createdAt;
}
