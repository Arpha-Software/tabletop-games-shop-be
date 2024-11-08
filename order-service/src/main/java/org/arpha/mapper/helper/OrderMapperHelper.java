package org.arpha.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.arpha.entity.User;
import org.arpha.repository.ProductRepository;
import org.arpha.security.UserDetailsAdapter;
import org.mapstruct.Named;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapperHelper {

    @Named("getUser")
    public User getUser(Object plug) {
        return  ((UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user();
    }

}
