package com.arango.auction.controller;

import com.arango.auction.model.Item;
import com.arango.auction.model.User;
import com.arango.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/add")
    public void addUser(@RequestBody User user){userService.addUser(user);
    }

    @PostMapping(value = ("/delete/{id}"))
    public void deleteUser(@PathVariable(value = "id")Long userId) {
        userService.deleteUser(userId);
    }
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();

    }
}
