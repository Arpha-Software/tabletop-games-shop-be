package org.arpha.controller;

import jakarta.validation.Valid;
import org.arpha.dto.user.request.CreateUserRequest;
import org.arpha.dto.user.response.CreateUserResponse;
import org.arpha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public CreateUserResponse create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest);
    }

}
