package com.example.usermanager.controller;

import com.example.usermanager.exception.DataProcessingException;
import com.example.usermanager.mapper.UserAccountMapper;
import com.example.usermanager.model.Role;
import com.example.usermanager.model.Role.RoleName;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.model.dto.UserAccountRequestDto;
import com.example.usermanager.model.dto.UserAccountResponseDto;
import com.example.usermanager.service.RoleService;
import com.example.usermanager.service.UserAccountService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserAccountController {
    private RoleService roleService;
    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;

    public UserAccountController(RoleService roleService,
                                 UserAccountService userAccountService,
                                 UserAccountMapper userAccountMapper) {
        this.roleService = roleService;
        this.userAccountService = userAccountService;
        this.userAccountMapper = userAccountMapper;
    }

    @GetMapping
    public List<UserAccountResponseDto> findAll(@RequestParam Map<String, String> params) {
        return userAccountService.findAll(params)
                .stream()
                .map(userAccountMapper::modelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserAccountResponseDto findById(@PathVariable Long id) {
        return userAccountMapper.modelToDto(userAccountService.getById(id));
    }

    @PostMapping("/new")
    public UserAccountResponseDto create(@RequestBody
                                             @Valid UserAccountRequestDto userAccountRequestDto) {
        Optional<UserAccount> userFromDb = userAccountService
                .getUserByUsername(userAccountRequestDto.getUsername());
        if (userFromDb.isPresent()) {
            throw new DataProcessingException("User is already present in db, username: "
                    + userAccountRequestDto.getUsername());
        }
        UserAccount userAccount = userAccountMapper.dtoToModel(userAccountRequestDto);
        userAccount.setCreatedAt(LocalDateTime.now().withNano(0));
        userAccount = userAccountService.save(userAccount);
        return userAccountMapper.modelToDto(userAccount);
    }

    @PutMapping("/{id}/edit")
    public UserAccountResponseDto update(@PathVariable Long id,
                                         @RequestBody
                                         @Valid UserAccountRequestDto userAccountRequestDto) {
        UserAccount userAccount = userAccountMapper.dtoToModel(userAccountRequestDto);
        userAccount.setId(id);
        userAccount.setCreatedAt(userAccountService.getById(id).getCreatedAt());
        userAccount = userAccountService.update(userAccount);
        return userAccountMapper.modelToDto(userAccount);
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
