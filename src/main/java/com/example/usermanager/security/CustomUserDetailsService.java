package com.example.usermanager.security;

import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.service.UserAccountService;
import java.util.Optional;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserAccountService userAccountService;

    public CustomUserDetailsService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> optionalUser = userAccountService.getUserByUsername(username);
        UserBuilder builder;
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Wrong username or password");
        }
        UserAccount existingUser = optionalUser.get();
        if (existingUser.getStatus().equals(Status.INACTIVE)) {
            throw new RuntimeException("User with this username is INACTIVE");
        }
        builder = org.springframework.security.core.userdetails.User.withUsername(username);
        builder.password(existingUser.getPassword());
        builder.roles(existingUser.getRoles()
                .stream()
                .map(role -> role.getRoleName().name())
                .toArray(String[]::new));
        return builder.build();
    }
}
