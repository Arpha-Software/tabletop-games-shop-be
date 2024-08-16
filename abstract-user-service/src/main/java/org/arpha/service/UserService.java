package org.arpha.service;

import org.arpha.dto.user.request.ChangePasswordRequest;
import org.arpha.dto.user.request.CreateUserRequest;
import org.arpha.dto.user.response.ChangePasswordResponse;
import org.arpha.dto.user.response.CreateUserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    CreateUserResponse create(CreateUserRequest createUserRequest);

    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest);

}
