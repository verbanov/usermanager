package com.example.usermanager.controller;

import static org.mockito.ArgumentMatchers.any;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.model.dto.UserAccountResponseDto;
import com.example.usermanager.service.UserAccountService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
class UserAccountControllerTest {
    @MockBean
    private UserAccountService userAccountService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @WithMockUser(username = "bob", password = "bob1234")
    @Test
    void shouldReturnAllUsers() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        userRole.setId(1L);
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);
        adminRole.setId(2L);

        UserAccount bob = new UserAccount();
        bob.setId(1L);
        bob.setUsername("bob");
        bob.setPassword("$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm");
        bob.setFirstName("FirstBob");
        bob.setLastName("LastBob");
        bob.setRoles(Set.of(userRole));
        bob.setStatus(Status.valueOf("ACTIVE"));
        bob.setCreatedAt(LocalDateTime.now().withNano(0));

        UserAccount alice = new UserAccount();
        alice.setId(2L);
        alice.setUsername("alice");
        alice.setPassword("$2a$10$OjgQ6CmdWlexj77FY1jNB.N5XJrv9Dbxh1k0cBd7SKDYtu7TeaX4O");
        alice.setFirstName("Firstalice");
        alice.setLastName("Lastalice");
        alice.setRoles(Set.of(adminRole));
        alice.setStatus(Status.valueOf("ACTIVE"));
        alice.setCreatedAt(LocalDateTime.now().withNano(0));

        UserAccount john = new UserAccount();
        john.setId(3L);
        john.setUsername("john");
        john.setPassword("$2a$10$z9.YuqGznOF5aZc0vUqvnOrN.uBBK16OmhsMne1qisycd.u//SmRa");
        john.setFirstName("Firstjohn");
        john.setLastName("Lastjohn");
        john.setRoles(Set.of(userRole));
        john.setStatus(Status.valueOf("INACTIVE"));
        john.setCreatedAt(LocalDateTime.now().withNano(0));

        UserAccountResponseDto bobDto = new UserAccountResponseDto();
        bobDto.setId(bob.getId());
        bobDto.setUsername(bob.getUsername());
        bobDto.setFirstName(bob.getFirstName());
        bobDto.setLastName(bob.getLastName());
        Set<String> bobRoles = bob.getRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toSet());
        bobDto.setRoles(bobRoles);
        bobDto.setStatus(bob.getStatus().name());
        bobDto.setCreatedAt(bob.getCreatedAt().toString());

        UserAccountResponseDto aliceDto = new UserAccountResponseDto();
        aliceDto.setId(alice.getId());
        aliceDto.setUsername(alice.getUsername());
        aliceDto.setFirstName(alice.getFirstName());
        aliceDto.setLastName(alice.getLastName());
        Set<String> aliceRoles = alice.getRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toSet());
        aliceDto.setRoles(aliceRoles);
        aliceDto.setStatus(alice.getStatus().name());
        aliceDto.setCreatedAt(alice.getCreatedAt().toString());

        UserAccountResponseDto johnDto = new UserAccountResponseDto();
        johnDto.setId(john.getId());
        johnDto.setUsername(john.getUsername());
        johnDto.setFirstName(john.getFirstName());
        johnDto.setLastName(john.getLastName());
        Set<String> johnRoles = john.getRoles()
                .stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toSet());
        johnDto.setRoles(johnRoles);
        johnDto.setStatus(john.getStatus().name());
        johnDto.setCreatedAt(john.getCreatedAt().toString());
        List<UserAccountResponseDto> mockUserDtos = List.of(bobDto, aliceDto, johnDto);

        List<UserAccount> mockUsers = List.of(bob, alice, john);
        Mockito.when(userAccountService.findAll(any())).thenReturn(mockUsers);

        RestAssuredMockMvc
                .given()
                .queryParam("params", "")
                .when()
                .get("/user")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(3));

    }

    @Test
    void ShouldCreateUserAccount() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        userRole.setId(1L);
        UserAccount userToSave = new UserAccount();
        userToSave.setUsername("bob");
        userToSave.setPassword("$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm");
        userToSave.setFirstName("FirstBob");
        userToSave.setLastName("LastBob");
        userToSave.setRoles(Set.of(userRole));
        userToSave.setStatus(Status.valueOf("ACTIVE"));
        userToSave.setCreatedAt(LocalDateTime.now().withNano(0));
        //нет смысла тестировать если мы подставляем значения на выходе в том числе
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }
}
