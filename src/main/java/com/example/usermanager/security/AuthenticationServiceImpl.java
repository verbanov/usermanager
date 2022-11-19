package com.example.usermanager.security;

import com.example.usermanager.exception.DataProcessingException;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.service.UserAccountService;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserAccountService userAccountService,
                                     PasswordEncoder passwordEncoder) {
        this.userAccountService = userAccountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount login(String login, String password) {
        Optional<UserAccount> optionalUser = userAccountService.getUserByUsername(login);
        String encodePassword = passwordEncoder.encode(password);
        if (optionalUser.isEmpty() || optionalUser.get().getPassword().equals(encodePassword)) {
            throw new DataProcessingException("Wrong username or password");
        }
        if (optionalUser.get().getStatus().equals(Status.INACTIVE)) {
            throw new DataProcessingException("User with this username is INACTIVE");
        }
        return optionalUser.get();
    }
}
