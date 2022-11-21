package com.example.usermanager.security;

import com.example.usermanager.exception.DataProcessingException;
import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.service.UserAccountServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.shaded.org.bouncycastle.math.ec.custom.sec.SecT113Field;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    private String username;
    private String password;
    private UserAccount bob;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private UserAccountServiceImpl userAccountService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        username = "bob";
        password = "bob1234";
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        userRole.setId(1L);
        bob = new UserAccount();
        bob.setUsername(username);
        bob.setPassword("$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm");
        bob.setFirstName("FirstBob");
        bob.setLastName("LastBob");
        bob.setRoles(Set.of(userRole));
        bob.setStatus(Status.valueOf("ACTIVE"));
        bob.setCreatedAt(LocalDateTime.now().withNano(0));
    }

    @Test
    void shouldReturnUserAccount() {
        Mockito.when(userAccountService.getUserByUsername(username)).thenReturn(Optional.of(bob));
        Mockito.when(passwordEncoder.matches(password, bob.getPassword())).thenReturn(true);
        UserAccount expected = bob;
        UserAccount actual = authenticationService.login(username, password);
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getCreatedAt());
        Assert.assertEquals(actual,expected);
    }

    @Test
    void shouldReturnDataProcessingExceptionWhenUserIsAbsent() {
        String userIsAbsent = "userIsAbsent";
        Mockito.when(userAccountService.getUserByUsername(userIsAbsent)).thenReturn(Optional.empty());
        String expectedMessage = "Wrong username or password";
        Exception exception = Assert.assertThrows(DataProcessingException.class, () -> {
            authenticationService.login(userIsAbsent, password);
        });
        String actualMessage = exception.getMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldReturnDataProcessingExceptionWhenPasswordIsIncorrect() {
        String incorrectPassword = "incorrectPassword";
        Mockito.when(userAccountService.getUserByUsername(username)).thenReturn(Optional.of(bob));
        Mockito.when(passwordEncoder.matches(incorrectPassword, bob.getPassword())).thenReturn(false);        String expectedMessage = "Wrong username or password";
        Exception exception = Assert.assertThrows(DataProcessingException.class, () -> {
            authenticationService.login(username, incorrectPassword);
        });
        String actualMessage = exception.getMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldReturnDataProcessingExceptionWhenUserIsInactive() {
        bob.setStatus(Status.INACTIVE);
        Mockito.when(userAccountService.getUserByUsername(username)).thenReturn(Optional.of(bob));
        Mockito.when(passwordEncoder.matches(password, bob.getPassword())).thenReturn(true);
        String expectedMessage = "User with this username is INACTIVE";
        Exception exception = Assert.assertThrows(DataProcessingException.class, () -> {
            authenticationService.login(username, password);
        });
        String actualMessage = exception.getMessage();
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }
}