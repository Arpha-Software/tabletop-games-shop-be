package org.arpha.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.arpha.dto.order.response.OrderAnalyticsDto;
import org.arpha.dto.user.request.UpdateUserRequest;
import org.arpha.dto.user.response.UserResponse;
import org.arpha.dto.user.response.analytics.UserAnalyticsResponse;
import org.arpha.entity.User;
import org.arpha.exception.EmailAlreadyTakenException;
import org.arpha.exception.UserNotFoundException;
import org.arpha.mapper.AnalyticsMapper;
import org.arpha.mapper.UserMapper;
import org.arpha.repository.UserRepository;
import org.arpha.security.UserDetailsAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    public static final String USER_NOT_FOUND_MESSAGE = "User with %d id doesn't exist!";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderService orderService;
    private final AnalyticsMapper analyticsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional
                .of(username)
                .flatMap(userRepository::findByEmail)
                .map(UserDetailsAdapter::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with %s email wasn't found!".formatted(username)));
    }

    @Override
    public UserResponse createUser(String email, String firstName, String lastName) {
        return Optional
                .of(email)
                .filter(email1 -> !userRepository.existsByEmail(email1))
                .map(email1 -> userMapper.toUser(firstName, lastName, email1))
                .map(userRepository::save)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new EmailAlreadyTakenException("User with %s email already exists"
                        .formatted(email)));
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
    public Page<UserResponse> findAll(Predicate predicate, Pageable pageable) {
        return userRepository.findAll(predicate, pageable).map(userMapper::toUserResponse);
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
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        return Optional
                .of(email)
                .flatMap(userRepository::findByEmail)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new UserNotFoundException("User with %s email doesn't exist!".formatted(email)));
    }

    @Override
    @Transactional(readOnly = true)
    public UserAnalyticsResponse getUserAnalytics(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE.formatted(userId)));

        List<OrderAnalyticsDto> orders = orderService.findAllByUserId(userId, Sort.by(Sort.Direction.DESC, "createdAt"));

        return analyticsMapper.toUserAnalyticsResponse(user, orders);
    }
}
