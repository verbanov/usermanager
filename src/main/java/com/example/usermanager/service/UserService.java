package com.example.usermanager.service;

import com.example.usermanager.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User getById(Long id);

    User create(User user);

    User update(User user);
}
