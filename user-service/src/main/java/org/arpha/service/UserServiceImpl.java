package org.arpha.service;

import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.ChangePasswordRequest;
import org.arpha.dto.user.request.CreateUserRequest;
import org.arpha.dto.user.response.ChangePasswordResponse;
import org.arpha.dto.user.response.CreateUserResponse;
import org.arpha.entity.User;
import org.arpha.exception.ChangePasswordException;
import org.arpha.exception.EmailAlreadyTakenException;
import org.arpha.mapper.UserMapper;
import org.arpha.repository.UserRepository;
import org.arpha.security.UserDetailsAdapter;
import org.arpha.service.helper.UserServiceHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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
    public CreateUserResponse create(CreateUserRequest createUserRequest) {
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
        User user = ((UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user();
        return Optional
                .of(changePasswordRequest)
                .filter(changePasswordRequest1 -> passwordEncoder.matches(changePasswordRequest1.getPassword(), user.getPassword()))
                .map(changePasswordRequest1 -> userServiceHelper.updatePassword(user, changePasswordRequest1.getNewPassword()))
                .map(userRepository::save)
                .map(user1 -> new ChangePasswordResponse(user1.getId(), true))
                .orElseThrow(() -> new ChangePasswordException("Password wasn't changed because old password is wrong!"));
    }

    @Override
    public boolean existById(long id) {
        return userRepository.existsById(id);
    }
}
