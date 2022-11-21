package com.example.usermanager.service;

import com.example.usermanager.exception.DataProcessingException;
import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.repository.UserAccountRepository;
import com.example.usermanager.repository.specification.SpecificationManager;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final SpecificationManager<UserAccount> userAccountSpecificationManager;
    private final PasswordEncoder passwordEncoder;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository,
                                  SpecificationManager<UserAccount>
                                          userAccountSpecificationManager,
                                  PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountSpecificationManager = userAccountSpecificationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserAccount> getUserByUsername(String userName) {
        return userAccountRepository.getUserByUsername(userName);
    }

    @Override
    public UserAccount register(UserAccount userAccount) {
        Optional<UserAccount> userFromDb = userAccountRepository.getUserByUsername(userAccount.getUsername());
        if (userFromDb.isPresent()) {
            throw new DataProcessingException("User is already present in db, username: "
                    + userAccount.getUsername());
        }
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setCreatedAt(LocalDateTime.now().withNano(0));
        userAccount.setStatus(Status.INACTIVE);
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount update(UserAccount userAccount) {
        userAccount.setCreatedAt(getById(userAccount.getId()).getCreatedAt());
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount getById(Long id) {
        return userAccountRepository.getUserById(id).orElseThrow(
                () -> new DataProcessingException("Can't get user with id " + id));
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

    @Override
    public UserAccount changeStatus(Long id) {
        UserAccount userAccount = getById(id);
        Status status = userAccount.getStatus();
        Status[] values = Status.values();
        userAccount.setStatus(Arrays.stream(values)
                .filter(s -> s.name() != status.name())
                .findFirst()
                .orElseThrow(
                        () -> new DataProcessingException("Can't change status for user with id " + id)));
        return userAccountRepository.save(userAccount);
    }
}
