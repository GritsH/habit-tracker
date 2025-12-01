package com.grits.habittracker.service;

import com.grits.habittracker.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);

    List<User> getAll();

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
