package com.github.muhammadkholidb.springcacheexample.service;

import com.github.muhammadkholidb.springcacheexample.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final CacheManager cacheManager;

    private final List<User> users = new ArrayList<>();

    @Cacheable(value = "users")
    public List<User> getUsers() {
        log.info("Get users");
        return users;
    }

    @Cacheable(value = "user")
    public User getUser(Integer id) {
        log.info("Get user by id: {}", id);
        return users.stream().filter(user -> user.getId().equals(id)).findAny().orElseThrow();
    }

    @CacheEvict(value = "users", allEntries = true)
    public User createUser(String name, String email) {
        log.info("Create user");
        User user = new User();
        user.setId(getNextUserId());
        user.setName(name);
        user.setEmail(email);
        users.add(user);
        return user;
    }

    @Caching(
            put = @CachePut(value = "user", key = "#id"),
            evict = @CacheEvict(value = "users", allEntries = true))
    public User updateUser(Integer id, String name, String email) {
        log.info("Update user by id: {}", id);
        return users.stream().filter(u -> u.getId().equals(id)).peek(u -> {
            u.setName(name);
            u.setEmail(email);
        }).findAny().orElseThrow();
    }

    private Integer getNextUserId() {
        int maxId = users.stream().mapToInt(User::getId).max().orElse(0);
        return maxId + 1;
    }

}
