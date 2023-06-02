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
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping(value = "/add")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping(value = ("/delete/{id}"))
    public ResponseEntity deleteUser(@PathVariable(value = "id")String userId) {
        return new ResponseEntity(userService.deleteUser(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        return userService.findAll();

    }


}
