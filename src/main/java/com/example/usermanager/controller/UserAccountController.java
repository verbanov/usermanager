package com.example.usermanager.controller;

import com.example.usermanager.exception.DataProcessingException;
import com.example.usermanager.mapper.UserAccountMapper;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.model.dto.UserAccountRequestDto;
import com.example.usermanager.model.dto.UserAccountResponseDto;
import com.example.usermanager.service.RoleService;
import com.example.usermanager.service.UserAccountService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<UserAccountResponseDto> findAll() {
        return userAccountService.findAll()
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
}
