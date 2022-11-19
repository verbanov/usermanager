package com.example.usermanager.service;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import com.example.usermanager.security.CustomUserDetailsService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    private UserAccountServiceImpl userAccountService;
    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    void shouldReturnUserDetailsWithCurrentUserData() {
        String username = "bob";

        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        userRole.setId(1L);
        UserAccount bob = new UserAccount();
        bob.setUsername(username);
        bob.setPassword("$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm");
        bob.setFirstName("FirstBob");
        bob.setLastName("LastBob");
        bob.setRoles(Set.of(userRole));
        bob.setStatus(Status.valueOf("ACTIVE"));
        bob.setCreatedAt(LocalDateTime.now().withNano(0));

        Mockito.when(userAccountService.getUserByUsername(username)).thenReturn(Optional.of(bob));

        UserDetails actual = userDetailsService.loadUserByUsername(username);

        Assert.assertNotNull(actual);
        Assert.assertEquals(bob.getUsername(), actual.getUsername());
        Assert.assertEquals(bob.getPassword(), actual.getPassword());
    }
}
