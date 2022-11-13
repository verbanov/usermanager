package com.example.usermanager.service;

import com.example.usermanager.exception.DataProcessingException;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.repository.UserAccountRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount getById(Long id) {
        return userAccountRepository.getUserById(id).orElseThrow(
                () -> new DataProcessingException("Can't get user with id " + id));
    }

    @Override
    public Optional<UserAccount> getUserByUsername(String userName) {
        return userAccountRepository.getUserByUsername(userName);
    }

    @Override
    public List<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }
}
