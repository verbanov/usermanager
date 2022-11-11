package com.example.usermanager.controller;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.User;
import com.example.usermanager.service.RoleService;
import com.example.usermanager.service.UserService;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private UserService userService;
    private RoleService roleService;

    public InjectController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String inject() {
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);
        userRole = roleService.save(userRole);
        adminRole = roleService.save(adminRole);

        User bob = new User();
        bob.setUsername("bob");
        bob.setPassword("$2a$10$LnT3pLUtTatnl1Ze73M3GuVrCIstVlXh778APMxkjyQ6u6LjWQAmm");
        //bob1234
        bob.setFirstName("FirstBob");
        bob.setLastName("LastBob");
        bob.setRoles(Set.of(adminRole));
        bob.setStatus(Status.valueOf("ACTIVE"));
        bob.setCreatedAt(LocalDateTime.now().withNano(0));
        userService.save(bob);

        User alice = new User();
        alice.setUsername("alice");
        alice.setPassword("$2a$10$OjgQ6CmdWlexj77FY1jNB.N5XJrv9Dbxh1k0cBd7SKDYtu7TeaX4O");
        //alice1234
        alice.setFirstName("Firstalice");
        alice.setLastName("Lastalice");
        alice.setRoles(Set.of(userRole));
        alice.setStatus(Status.valueOf("ACTIVE"));
        alice.setCreatedAt(LocalDateTime.now().withNano(0));
        userService.save(alice);

        User john = new User();
        john.setUsername("john");
        john.setPassword("$2a$10$z9.YuqGznOF5aZc0vUqvnOrN.uBBK16OmhsMne1qisycd.u//SmRa");
        //john1234
        john.setFirstName("Firstjohn");
        john.setLastName("Lastjohn");
        john.setRoles(Set.of(userRole));
        john.setStatus(Status.valueOf("INACTIVE"));
        john.setCreatedAt(LocalDateTime.now().withNano(0));
        userService.save(john);
        return "Done!";
    }

//    public static void main(String[] args) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String password = "bob1234";
//        System.out.println(bCryptPasswordEncoder.encode(password));
//        String password2 = "alice1234";
//        System.out.println(bCryptPasswordEncoder.encode(password2));
//        String password3 = "john1234";
//        System.out.println(bCryptPasswordEncoder.encode(password3));
//    }
}
