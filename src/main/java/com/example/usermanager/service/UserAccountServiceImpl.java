package com.example.usermanager.service;

import com.example.usermanager.model.Status;
import com.example.usermanager.model.UserAccount;
import com.example.usermanager.repository.UserAccountRepository;
import com.example.usermanager.repository.specification.SpecificationManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private static Map<Status, Status> statuses;
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
        Optional<UserAccount> userFromDb = userAccountRepository
                .getUserByUsername(userAccount.getUsername());
        if (userFromDb.isPresent()) {
            throw new RuntimeException("User is already present in db, username: "
                    + userAccount.getUsername());
        }
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
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
                () -> new RuntimeException("Can't get user with id " + id));
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
        Status currentStatus = userAccount.getStatus();
        Status newStatus = statuses.get(currentStatus);
        userAccount.setStatus(newStatus);
        return userAccountRepository.save(userAccount);
    }

    @PostConstruct
    public void createStatusMap() {
        statuses = new HashMap<>();
        Status[] values = Status.values();
        for (Status value : values) {
            statuses.put(value, Arrays.stream(values)
                    .filter(v -> !v.name().equals(value.name()))
                    .findFirst().get());
        }
    }
}
