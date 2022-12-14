package com.example.usermanager.service;

import com.example.usermanager.model.UserAccount;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserAccountService {
    UserAccount save(UserAccount userAccount);

    UserAccount register(UserAccount userAccount);

    UserAccount update(UserAccount userAccount);

    UserAccount getById(Long id);

    Optional<UserAccount> getUserByUsername(String userName);

    List<UserAccount> findAll(Map<String, String> params);

    UserAccount changeStatus(Long id);
}
