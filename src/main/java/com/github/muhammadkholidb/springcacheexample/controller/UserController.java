package com.github.muhammadkholidb.springcacheexample.controller;

import com.github.muhammadkholidb.springcacheexample.model.User;
import com.github.muhammadkholidb.springcacheexample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/users")
    public User createUser(@RequestParam String name, @RequestParam String email) {
        return userService.createUser(name, email);
    }

    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable Integer userId, @RequestParam String name, @RequestParam String email) {
        return userService.updateUser(userId, name, email);
    }

}
