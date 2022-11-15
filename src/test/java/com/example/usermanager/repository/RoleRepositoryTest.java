package com.example.usermanager.repository;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import org.junit.jupiter.api.Assertions;
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

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
class RoleRepositoryTest {
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

    @BeforeEach
    void setUp() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);
        roleRepository.save(userRole);
        roleRepository.save(adminRole);
    }

    @Test
    void shouldReturnRoleByRoleName_Ok() {
        RoleName userRole = RoleName.USER;
        Role expected = new Role();
        expected.setId(1L);
        expected.setRoleName(RoleName.USER);
        Role actual = roleRepository.getRoleByRoleName(userRole);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getRoleName(), actual.getRoleName());
    }
}
