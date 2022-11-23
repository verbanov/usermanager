package com.example.usermanager.repository;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.UserAccount;
import java.util.Set;
import org.junit.Assert;
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
        propertyRegistry.add("spring.datasource.password", database::getPassword);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
    }

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    void shouldReturnUserAccountByUserName() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        userRole.setId(1L);
        UserAccount expected = new UserAccount();
        String userName = "bob";
        expected.setId(1L);
        expected.setUsername(userName);
        expected.setPassword("$2a$10$OfLJB.nP5INfLEbuN4zck.mg7vN3y04/XRImnjK0iPakTu7VtRwnC");
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
