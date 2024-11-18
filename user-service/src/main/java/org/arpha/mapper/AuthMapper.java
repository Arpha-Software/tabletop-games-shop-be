package org.arpha.mapper;

import org.arpha.dto.user.UserDetails;
import org.arpha.dto.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "id", source = "id")
    UserDetails toUserDetails(UserResponse userResponse);

}
