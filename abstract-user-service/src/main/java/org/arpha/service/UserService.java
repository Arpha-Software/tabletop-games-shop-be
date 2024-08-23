package org.arpha.service;

import org.arpha.dto.user.request.ChangePasswordRequest;
import org.arpha.dto.user.request.CreateUserRequest;
import org.arpha.dto.user.request.UpdateUserRequest;
import org.arpha.dto.user.response.ChangePasswordResponse;
import org.arpha.dto.user.response.CreateUserResponse;
import org.arpha.dto.user.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    CreateUserResponse create(CreateUserRequest createUserRequest);

    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest);

    UserResponse updateUser(long userId, UpdateUserRequest updateUserRequest);

    void delete(long userId);

    void activateAccount(long userId);

    UserResponse findById(long userId);

    Page<UserResponse> findAll(Pageable pageable);

}
