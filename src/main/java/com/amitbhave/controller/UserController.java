package com.amitbhave.controller;

import com.amitbhave.entity.User;
import com.amitbhave.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;

import java.util.List;

@Controller("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Get
    public HttpResponse<List<User>> getUsers() {
        return HttpResponse.ok(userService.getUsers());
    }

    @Post
    public HttpResponse addUser(@Body User user) {
        return HttpResponse.created(userService.addUser(user));
    }

    @Delete("/{id}")
    public HttpResponse deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return HttpResponse.ok();
    }
}
