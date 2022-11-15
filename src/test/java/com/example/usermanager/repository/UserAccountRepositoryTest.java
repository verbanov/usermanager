package com.example.usermanager.repository;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import org.apache.tomcat.jni.User;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Set;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
class UserAccountRepositoryTest {
    @Container
    static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:15.1-alpine")
            .withDatabaseName("postgres")
            .withPassword("postgres")
            .withUsername("postgres");

    @DynamicPropertySource
    static void setDatabaseProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password=", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @BeforeEach
    void setUp() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);
        userRole = roleRepository.save(userRole);
        adminRole = roleRepository.save(adminRole);

        UserAccount bob = new UserAccount();
        bob.setUsername("bob");
        bob.setPassword("$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm");
        bob.setFirstName("FirstBob");
        bob.setLastName("LastBob");
        bob.setRoles(Set.of(userRole));
        bob.setStatus(Status.valueOf("ACTIVE"));
        bob.setCreatedAt(LocalDateTime.now().withNano(0));
        userAccountRepository.save(bob);
    }

    @Test
    void shouldReturnUserAccountByUserName_Ok() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        userRole.setId(1L);
        UserAccount expected = new UserAccount();
        String userName = "bob";
        expected.setId(1L);
        expected.setUsername(userName);
        expected.setPassword("$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm");
        expected.setFirstName("FirstBob");
        expected.setLastName("LastBob");
        expected.setRoles(Set.of(userRole));

        UserAccount actual = userAccountRepository.getUserByUsername(userName).orElseThrow(
                () -> new RuntimeException("Wrong userName"));
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getRoles(), actual.getRoles());
    }
}
