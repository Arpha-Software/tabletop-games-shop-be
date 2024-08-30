package org.arpha.core.security;

import lombok.RequiredArgsConstructor;
import org.arpha.security.UserDetailsAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthExpressions {


    public boolean isUserAllowed(long userId) {
        UserDetailsAdapter userDetails = (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.user().getId() == userId;
    }

}
