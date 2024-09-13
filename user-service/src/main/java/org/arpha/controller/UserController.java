package org.arpha.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.UpdateUserRequest;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.entity.User;
import org.arpha.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authExpressions.isUserAllowed(#id)")
    @PutMapping("/{id}")
    public UserResponse update(@PathVariable long id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        return userService.updateUser(id, updateUserRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authExpressions.isUserAllowed(#id)")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.deleteUserById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authExpressions.isUserAllowed(#id)")
    @GetMapping("/{id}")
    public UserResponse read(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public Page<UserResponse> findAll(@QuerydslPredicate(root = User.class) Predicate predicate, @PageableDefault Pageable pageable) {
        return userService.findAll(predicate, pageable);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @authExpressions.isUserAllowed(#id)")
    @PatchMapping("/{id}")
    public void activateUserAccount(@PathVariable long id) {
        userService.activateAccount(id);
    }

}
