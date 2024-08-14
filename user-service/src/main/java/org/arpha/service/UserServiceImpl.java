package org.arpha.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.user.request.CreateUserRequest;
import org.arpha.dto.user.response.CreateUserResponse;
import org.arpha.exception.EmailAlreadyTakenException;
import org.arpha.mapper.UserMapper;
import org.arpha.repository.UserRepository;
import org.arpha.security.UserDetailsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

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

}
