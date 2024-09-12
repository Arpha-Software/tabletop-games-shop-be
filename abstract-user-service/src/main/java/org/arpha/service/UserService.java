package org.arpha.service;

import com.querydsl.core.types.Predicate;
import org.arpha.dto.user.request.UpdateUserRequest;
import org.arpha.dto.user.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponse createUser(String email, String firstName, String lastName);

    UserResponse updateUser(long userId, UpdateUserRequest updateUserRequest);

    void deleteUserById(long userId);

    void activateAccount(long userId);

    UserResponse findUserById(long userId);

    Page<UserResponse> findAll(Predicate predicate, Pageable pageable);

    boolean existByEmail(String email);

    UserResponse findByEmail(String email);
}
