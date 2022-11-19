package com.example.usermanager.security;

import com.example.usermanager.model.UserAccount;

public interface AuthenticationService {
    UserAccount login(String login, String password);
}
