package com.amitbhave.service;

import com.amitbhave.entity.User;
import com.amitbhave.repository.UserRepository;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
