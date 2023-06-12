package com.arango.auction.service;

import com.arango.auction.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void deleteUser(Long userId);
    List<User> findAll();
}
