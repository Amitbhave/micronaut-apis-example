package com.amitbhave.service;

import com.amitbhave.entity.User;
import com.amitbhave.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Nested
    @DisplayName("Get users")
    class GetUsers {
        @Test
        public void shouldReturnEmptyListWhenNoUsersPresent() {
            when(userRepository.findAll()).thenReturn(emptyList());

            List<User> users = userService.getUsers();

            assertTrue(users.isEmpty());
        }

        @Test
        public void shouldReturnUsersWhenUsersPresent() {
            User user1 = new User(1l,"Virat","virat@gmail.com","Delhi");
            User user2 = new User(2l,"Rohit","rohit@gmail.com","Mumbai");
            List<User> users = List.of(user1, user2);
            when(userRepository.findAll()).thenReturn(users);

            List<User> actualUsers = userService.getUsers();

            assertEquals(users, actualUsers);
        }
    }

    @Test
    public void shouldSaveUser() {
        User user = new User("Virat", "virat@gmail.com", "Delhi");

        userService.addUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void shouldDeleteUser() {
        long userId = 1l;

        userService.deleteUser(userId);

        verify(userRepository).deleteById(userId);
    }

}