package com.example.usermanager.controller;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.service.RoleService;
import com.example.usermanager.service.UserAccountService;
import java.time.LocalDateTime;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private UserAccountService userAccountService;
    private RoleService roleService;

    public InjectController(UserAccountService userAccountService, RoleService roleService) {
        this.userAccountService = userAccountService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void inject() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);
        userRole = roleService.save(userRole);
        adminRole = roleService.save(adminRole);
        UserAccount bob = new UserAccount();
        bob.setUsername("bob");
        bob.setPassword("$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm");
        //bob1234
        bob.setFirstName("FirstBob");
        bob.setLastName("LastBob");
        bob.setRoles(Set.of(userRole));
        bob.setStatus(Status.valueOf("ACTIVE"));
        bob.setCreatedAt(LocalDateTime.now().withNano(0));
        userAccountService.save(bob);

        UserAccount alice = new UserAccount();
        alice.setUsername("alice");
        alice.setPassword("$2a$10$OjgQ6CmdWlexj77FY1jNB.N5XJrv9Dbxh1k0cBd7SKDYtu7TeaX4O");
        //alice1234
        alice.setFirstName("Firstalice");
        alice.setLastName("Lastalice");
        alice.setRoles(Set.of(adminRole));
        alice.setStatus(Status.valueOf("ACTIVE"));
        alice.setCreatedAt(LocalDateTime.now().withNano(0));
        userAccountService.save(alice);

        UserAccount john = new UserAccount();
        john.setUsername("john");
        john.setPassword("$2a$10$z9.YuqGznOF5aZc0vUqvnOrN.uBBK16OmhsMne1qisycd.u//SmRa");
        //john1234
        john.setFirstName("Firstjohn");
        john.setLastName("Lastjohn");
        john.setRoles(Set.of(userRole));
        john.setStatus(Status.valueOf("INACTIVE"));
        john.setCreatedAt(LocalDateTime.now().withNano(0));
        userAccountService.save(john);
    }
}
