package org.arpha.utills;

import lombok.experimental.UtilityClass;
import org.arpha.security.UserDetailsAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AspectUtils {

    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !authentication.getName().equals("anonymousUser") && authentication.getPrincipal() instanceof  UserDetailsAdapter userDetailsAdapter ? userDetailsAdapter.user().getId() : null;
    }

}
