package com.arango.auction.service;

import com.arango.auction.model.User;

import java.util.List;

public interface UserService {
    User addUser(User user);

    Object deleteUser(String userId);

    List<User> findAll();
}
