package org.arpha.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.querydsl.core.types.Predicate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.CreateUserDeliveryAddressRequest;
import org.arpha.dto.user.request.UpdateUserRequest;
import org.arpha.dto.user.response.UserDeliveryAddressResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.dto.user.response.analytics.UserAnalyticsResponse;
import org.arpha.entity.User;
import org.arpha.security.UserDetailsAdapter;
import org.arpha.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public UserResponse getAuthenticatedUser(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter) {
        return userService.findUserByEmail(userDetailsAdapter.getUsername());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PostMapping("/addresses")
    public UserDeliveryAddressResponse addAddress(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter, @RequestBody @Valid CreateUserDeliveryAddressRequest request) {
        return userService.addUserDeliveryAddress(userDetailsAdapter.user().getId(), request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/addresses")
    public List<UserDeliveryAddressResponse> getAddresses(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter) {
        return userService.getUserDeliveryAddresses(userDetailsAdapter.user().getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @DeleteMapping("/addresses/{addressId}")
    public void deleteAddress(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter, @PathVariable long addressId) {
        userService.deleteUserDeliveryAddress(userDetailsAdapter.user().getId(), addressId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PatchMapping("/addresses/{addressId}/set-default")
    public void setDefaultAddress(@AuthenticationPrincipal UserDetailsAdapter userDetailsAdapter, @PathVariable long addressId) {
        userService.setDefaultDeliveryAddress(userDetailsAdapter.user().getId(), addressId);
    }

    @Operation(summary = "Get user analytics",
            description = "Retrieves comprehensive analytics for a single user, including order statistics and history.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved analytics",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserAnalyticsResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden - user does not have permission"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            })
    @PreAuthorize("hasRole('ROLE_ADMIN') or @authExpressions.isUserAllowed(#id)")
    @GetMapping("/{id}/analytics")
    public UserAnalyticsResponse getAnalytics(@PathVariable long id) {
        return userService.getUserAnalytics(id);
    }

}
