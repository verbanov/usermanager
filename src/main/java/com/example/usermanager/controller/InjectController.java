package com.example.usermanager.controller;

import com.example.usermanager.model.Role;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.User;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    @GetMapping
    public String inject() {
        User bob = new User();
        bob.setUsername("bob");
        bob.setPassword("bob1234");
        bob.setFirstName("FirstBob");
        bob.setLastName("LastBob");
        bob.setRole(Role.valueOf("ADMIN"));
        bob.setStatus(Status.valueOf("ACTIVE"));
        bob.setCreatedAt(LocalDateTime.now());

        User alice = new User();
        alice.setUsername("alice");
        alice.setPassword("alice1234");
        alice.setFirstName("Firstalice");
        alice.setLastName("Lastalice");
        alice.setRole(Role.valueOf("USER"));
        alice.setStatus(Status.valueOf("ACTIVE"));
        alice.setCreatedAt(LocalDateTime.now());

        User john = new User();
        john.setUsername("john");
        john.setPassword("john1234");
        john.setFirstName("Firstjohn");
        john.setLastName("Lastjohn");
        john.setRole(Role.valueOf("USER"));
        john.setStatus(Status.valueOf("INACTIVE"));
        john.setCreatedAt(LocalDateTime.now());
        return "Done!";
    }
}
