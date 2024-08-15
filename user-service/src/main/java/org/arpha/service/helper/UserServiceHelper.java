package org.arpha.service.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {

    private final PasswordEncoder passwordEncoder;

    public User updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }
}
