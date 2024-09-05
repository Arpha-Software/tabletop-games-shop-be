package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.ChangePasswordRequest;
import org.arpha.dto.user.request.CreateUserRequest;
import org.arpha.dto.user.request.UpdateUserRequest;
import org.arpha.dto.user.response.ChangePasswordResponse;
import org.arpha.dto.user.response.CreateUserResponse;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.entity.User;
import org.arpha.exception.ChangePasswordException;
import org.arpha.exception.EmailAlreadyTakenException;
import org.arpha.exception.UserNotFoundException;
import org.arpha.mapper.UserMapper;
import org.arpha.repository.UserRepository;
import org.arpha.security.UserDetailsAdapter;
import org.arpha.service.helper.UserServiceHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND_MESSAGE = "User with %d id doesn't exist!";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceHelper userServiceHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional
                .of(username)
                .flatMap(userRepository::findByEmail)
                .map(UserDetailsAdapter::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with %s email wasn't found!".formatted(username)));
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        return Optional
                .of(createUserRequest)
                .filter(createUserRequest1 -> !userRepository.existsByEmail(createUserRequest1.getEmail()))
                .map(userMapper::toUser)
                .map(userRepository::save)
                .map(userMapper::toCreateUserResponse)
                .orElseThrow(() -> new EmailAlreadyTakenException("User with %s email already exists"
                        .formatted(createUserRequest.getEmail())));
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository
                .findById(changePasswordRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE.formatted(changePasswordRequest.getUserId())));

        return Optional
                .of(changePasswordRequest)
                .filter(changePasswordRequest1 -> passwordEncoder.matches(changePasswordRequest1.getPassword(), user.getPassword()))
                .map(changePasswordRequest1 -> userServiceHelper.updatePassword(user, changePasswordRequest1.getNewPassword()))
                .map(userRepository::save)
                .map(user1 -> new ChangePasswordResponse(user1.getId(), true))
                .orElseThrow(() -> new ChangePasswordException("Password wasn't changed because old password is wrong!"));
    }

    @Override
    public UserResponse updateUser(long userId, UpdateUserRequest updateUserRequest) {
        return Optional
                .of(userId)
                .flatMap(userRepository::findById)
                .map(user -> userMapper.updateUser(user, updateUserRequest))
                .map(userRepository::save)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE.formatted(userId)));
    }

    @Override
    public void deleteUserById(long userId) {
        Optional
                .of(userId)
                .flatMap(userRepository::findById)
                .ifPresent(user -> {
                    user.setActive(false);
                    userRepository.save(user);
                });
    }

    @Override
    public UserResponse findUserById(long userId) {
        return Optional
                .of(userId)
                .flatMap(userRepository::findById)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE.formatted(userId)));
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toUserResponse);
    }

    @Override
    public void activateAccount(long userId) {
        Optional
                .of(userId)
                .flatMap(userRepository::findById)
                .ifPresent(user -> {
                    user.setActive(true);
                    userRepository.save(user);
                });
    }

    @Override
    public boolean existById(long id) {
        return userRepository.existsById(id);
    }

}
