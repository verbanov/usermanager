package com.example.usermanager.controller;

import com.example.usermanager.mapper.UserAccountMapper;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.model.dto.UserAccountRequestDto;
import com.example.usermanager.model.dto.UserAccountResponseDto;
import com.example.usermanager.service.UserAccountService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
@RequestMapping("/users")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;

    public UserAccountController(UserAccountService userAccountService,
                                 UserAccountMapper userAccountMapper) {
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

    @PostMapping
    public UserAccountResponseDto create(@RequestBody
                                             @Valid UserAccountRequestDto userAccountRequestDto) {
        UserAccount userAccount = userAccountMapper.dtoToModel(userAccountRequestDto);
        userAccount = userAccountService.register(userAccount);
        return userAccountMapper.modelToDto(userAccount);
    }

    @PutMapping("/{id}")
    public UserAccountResponseDto update(@PathVariable Long id,
                                         @RequestBody
                                         @Valid UserAccountRequestDto userAccountRequestDto) {
        UserAccount userAccount = userAccountMapper.dtoToModel(userAccountRequestDto);
        userAccount.setId(id);
        userAccount = userAccountService.update(userAccount);
        return userAccountMapper.modelToDto(userAccount);
    }

    @PutMapping("/change-status")
    public UserAccountResponseDto lockUnlock(@RequestParam Long id) {
        return userAccountMapper.modelToDto(userAccountService.changeStatus(id));
    }
}
