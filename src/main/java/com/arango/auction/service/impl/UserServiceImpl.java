package com.arango.auction.service.impl;

import com.arango.auction.model.User;
import com.arango.auction.repository.UserRepository;
import com.arango.auction.service.UserService;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private DSLContext dslContext;
    @Override
    public User addUser(User user) {
        return dslContext.transactionResult(()-> userRepository.insert(user));
    }

    @Override
    public Object deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found!"));
        userRepository.deleteById(userId);
        return "User Deleted Successfully";
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
