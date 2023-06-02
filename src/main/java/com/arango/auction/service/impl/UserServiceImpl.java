package com.arango.auction.service.impl;

import com.arango.auction.model.Item;
import com.arango.auction.model.User;
import com.arango.auction.repository.UserRepository;
import com.arango.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Object deleteUser(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isPresent()){
            userRepository.deleteById(userId);
            return "User Deleted Successfully";
        }else {
            return "User not found!";
        }
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}