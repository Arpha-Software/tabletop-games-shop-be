package org.arpha.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.ChangePasswordRequest;
import org.arpha.dto.user.request.CreateUserRequest;
import org.arpha.dto.user.response.ChangePasswordResponse;
import org.arpha.dto.user.response.CreateUserResponse;
import org.arpha.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CreateUserResponse create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest);
    }

    @PostMapping("/password/change")
    public ChangePasswordResponse changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }

}
