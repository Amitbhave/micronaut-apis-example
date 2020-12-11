package com.amitbhave.controller;

import com.amitbhave.entity.User;
import com.amitbhave.repository.UserRepository;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(transactional = false)
class UserControllerTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    @Client("/api")
    RxHttpClient httpClient;

    @Inject
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void shouldGetEmptyListWhenNoUsers() {
        HttpResponse<List> response = httpClient.toBlocking().exchange(HttpRequest.GET("/users"), Argument.of(List.class, User.class));
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.body().isEmpty());
    }

    @Test
    void shouldReturnListOfUsersWhenUsersPresent() {
        User user = new User("Virat", "virat@gmail.com", "Delhi");
        userRepository.save(user);
        HttpResponse<List> response = httpClient.toBlocking().exchange(HttpRequest.GET("/users"), Argument.of(List.class, User.class));
        assertEquals(HttpStatus.OK, response.getStatus());
        List<User> actualUsers = response.body();
        assertFalse(actualUsers.isEmpty());
        assertTrue(new ReflectionEquals(actualUsers.get(0), new String[]{"id"}).matches(user));
    }

    @Test
    void shouldSaveUser() {
        User user = new User("Virat", "virat@gmail.com", "Delhi");
        HttpResponse response = httpClient.toBlocking().exchange(HttpRequest.POST("/users", user));
        assertEquals(HttpStatus.CREATED, response.getStatus());

        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
        assertTrue(new ReflectionEquals(users.get(0), new String[]{"id"}).matches(user));
    }

    @Test
    void shouldDeleteUser() {
        User user = new User("Virat", "virat@gmail.com", "Delhi");
        userRepository.save(user);
        User savedUser = userRepository.findAll().get(0);

        HttpResponse response = httpClient.toBlocking().exchange(HttpRequest.DELETE("/users/" + savedUser.getId()));
        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(userRepository.findAll().isEmpty());
    }

}