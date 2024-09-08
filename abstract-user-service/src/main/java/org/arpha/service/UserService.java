package org.arpha.service;

import com.querydsl.core.types.Predicate;
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

    CreateUserResponse createUser(CreateUserRequest createUserRequest);

    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest);

    UserResponse updateUser(long userId, UpdateUserRequest updateUserRequest);

    void deleteUserById(long userId);

    void activateAccount(long userId);

    UserResponse findUserById(long userId);

    Page<UserResponse> findAll(Predicate predicate, Pageable pageable);

    boolean existById(long id);
}
