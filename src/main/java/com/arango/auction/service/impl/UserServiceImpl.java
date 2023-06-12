package com.arango.auction.service.impl;

import com.arango.auction.constants.AuctionExceptions;
import com.arango.auction.model.User;
import com.arango.auction.repository.UserRepository;
import com.arango.auction.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DSLContext dslContext;

    @Override
    @Transactional
    public void addUser(User user) {
        User savedUser = userRepository.insert(user);
        log.info("User created with id:{}",savedUser.getUserId());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new AuctionExceptions("User not found!"));
        userRepository.deleteById(userId);
        log.info("User deleted");
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
