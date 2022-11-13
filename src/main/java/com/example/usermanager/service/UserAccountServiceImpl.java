package com.example.usermanager.service;

import com.example.usermanager.exception.DataProcessingException;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.repository.UserAccountRepository;
import com.example.usermanager.repository.specification.SpecificationManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final SpecificationManager<UserAccount> userAccountSpecificationManager;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository,
                                  SpecificationManager<UserAccount>
                                          userAccountSpecificationManager) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountSpecificationManager = userAccountSpecificationManager;
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
    public List<UserAccount> findAll(Map<String, String> params) {
        Specification<UserAccount> specification = null;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            Specification<UserAccount> sp = userAccountSpecificationManager.get(entry.getKey(),
                    entry.getValue().split(","));
            specification = specification == null
                    ? Specification.where(sp) : specification.and(sp);
        }
        return userAccountRepository.findAll(specification);
    }
}
